package ch.brownbag.layered;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "ch.brownbag.layered")
public class ArchangelsArchUnitTest {
    @ArchTest
    public static final ArchRule presentationShouldOnlyDependOnService = classes()
                .that()
                .resideInAPackage("..presentation..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..service..", "java..", "javax..", "org.springframework..");

    @ArchTest
    public static final ArchRule serviceOnlyAccessedByPresentation = classes()
            .that()
            .resideInAPackage("..service..")
            .should().onlyBeAccessed()
            .byAnyPackage("..presentation..", "..service..");

    @ArchTest
    public static final ArchRule presentationShouldNotTalkToPersistence = noClasses()
                .that()
                .resideInAPackage("..presentation..")
                .should().dependOnClassesThat()
                .resideInAPackage("..persistence..");

    @ArchTest
    public static final LayeredArchitecture arch = layeredArchitecture().consideringAllDependencies()
                .layer("Presentation").definedBy("..presentation..")
                .layer("Service").definedBy("..service..")
                .layer("Persistence").definedBy("..persistence..")
                // Add constraints
                .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Presentation")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");

    @ArchTest
    public static final ArchRule AllClassesInDTOShouldBeRecordsEndingWithDTO = classes()
                .that()
                .resideInAPackage("..dto..")
                .should()
                .beAssignableTo(Record.class)
                .andShould().haveSimpleNameEndingWith("DTO");

    @ArchTest
    public static final ArchRule notInDTOPackageAreNotCalledDTOs = classes()
                .that()
                .resideOutsideOfPackage("..dto..")
                .should().haveSimpleNameNotEndingWith("DTO");

    @ArchTest
    public static final ArchRule classesThatEndsWithControllerShouldBeAnnotatedAccordingly = classes()
            .that()
            .haveSimpleNameEndingWith("Controller")
            .should().beAnnotatedWith(RestController.class);

    @ArchTest
    public static final ArchRule noCycles = slices()
            .matching("ch.brownbag.layered.(*)..")
            .should().beFreeOfCycles();
}
