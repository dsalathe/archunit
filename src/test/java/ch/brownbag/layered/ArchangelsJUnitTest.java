package ch.brownbag.layered;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArchangelsJUnitTest {

    private static final JavaClasses jc = new ClassFileImporter().importPackages("ch.brownbag.layered");

    @Test
    public void givenPresentationLayerClasses_thenWrongCheckFails() {
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..presentation..")
                .should().onlyDependOnClassesThat()
                .resideInAPackage("..service..");

        //rule.check(jc);
        assertThrows(AssertionError.class, ()-> rule.check(jc)) ;
    }

    @Test
    public void givenPresentationLayerClasses_thenCheckWithFrameworkDependenciesSuccess() {
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..presentation..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..service..", "java..", "javax..", "org.springframework..");
        rule.check(jc);
    }

    @Test
    public void givenServiceLayerClasses_thenCheckOnlyAccessedByPresentationAndItself() {
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..service..")
                .should().onlyBeAccessed()
                .byAnyPackage("..presentation..", "..service..");
        rule.check(jc);
    }

    @Test
    public void givenPresentationLayerClasses_thenNoPersistenceLayerAccess() {
        ArchRule rule = noClasses()
                .that()
                .resideInAPackage("..presentation..")
                .should().dependOnClassesThat()
                .resideInAPackage("..persistence..");
        rule.check(jc);
    }

    @Test
    public void givenApplicationClasses_thenNoLayerViolationsShouldExist() {
        LayeredArchitecture arch = layeredArchitecture().consideringAllDependencies()
                .layer("Presentation").definedBy("..presentation..")
                .layer("Service").definedBy("..service..")
                .layer("Persistence").definedBy("..persistence..")
                // Add constraints
                .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Presentation")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");

        arch.check(jc);
    }


}
