package ch.brownbag.layered;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.metrics.ArchitectureMetrics;
import com.tngtech.archunit.library.metrics.ComponentDependencyMetrics;
import com.tngtech.archunit.library.metrics.MetricsComponents;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UncleBobMetrics { // aka Robert C. Martin

    private static final String ROOT_PACKAGE = "ch.brownbag.layered";
    private static final String SERVICE_PACKAGE = ROOT_PACKAGE + ".service";

    @Test
    public void metrics() {
        JavaClasses jc = new ClassFileImporter().importPackages(ROOT_PACKAGE);
        Set<JavaPackage> packages = jc.getPackage(ROOT_PACKAGE).getSubpackages();

        MetricsComponents<JavaClass> components = MetricsComponents.fromPackages(packages);

        ComponentDependencyMetrics metrics = ArchitectureMetrics.componentDependencyMetrics(components);

        System.out.println("Efferent Coupling (Ce) = " + metrics.getEfferentCoupling(SERVICE_PACKAGE));
        System.out.println("Afferent Coupling (Ca) = " + metrics.getAfferentCoupling(SERVICE_PACKAGE));
        System.out.println("Instability (I): Ce / (Ca + Ce) = " + metrics.getInstability(SERVICE_PACKAGE));//TODO understand
        System.out.println("Abstractness (A): #abstractClasses / #totalClasses = " + metrics.getAbstractness(SERVICE_PACKAGE));
        System.out.println("Normalized Distance From Main Sequence (D): |A + I - 1| = "
                + metrics.getNormalizedDistanceFromMainSequence(SERVICE_PACKAGE));
    }
}
