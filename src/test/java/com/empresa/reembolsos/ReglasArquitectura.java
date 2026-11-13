package com.empresa.reembolsos;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import com.empresa.reembolsos.adaptadores.rest.SolicitudReembolsoController;
import com.empresa.reembolsos.procesamiento.calculo.CalculadoraReembolso;
import com.empresa.reembolsos.procesamiento.notificacion.ObservadorCambioEstado;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
        packages = "com.empresa.reembolsos",
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ReglasArquitectura {
    @ArchTest
    static final ArchRule procesamientoSinPersistenciaNiWeb =
            noClasses()
                    .that()
                    .resideInAPackage("..procesamiento..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            "..infraestructura..",
                            "..adaptadores..",
                            "org.springframework.data..",
                            "jakarta.persistence..",
                            "org.springframework.web..",
                            "org.springframework.http..");

    @ArchTest
    static final ArchRule controladorSoloUsaServicio =
            classes()
                    .that()
                    .resideInAPackage("..adaptadores.rest..")
                    .should()
                    .onlyAccessClassesThat(
                            JavaClass.Predicates.resideInAnyPackage(
                                            "..aplicacion..",
                                            "..dominio..",
                                            "org.springframework.web..",
                                            "org.springframework.http..",
                                            "java..")
                                    .or(
                                            JavaClass.Predicates.equivalentTo(
                                                    SolicitudReembolsoController.class)));

    @ArchTest
    static final ArchRule calculadorasImplementanInterfaz =
            classes()
                    .that()
                    .resideInAPackage("..procesamiento.calculo..")
                    .and()
                    .areNotInterfaces()
                    .and()
                    .haveSimpleNameStartingWith("Calculadora")
                    .should()
                    .implement(CalculadoraReembolso.class);

    @ArchTest
    static final ArchRule notificadoresImplementanInterfaz =
            classes()
                    .that()
                    .resideInAPackage("..procesamiento.notificacion..")
                    .and()
                    .areNotInterfaces()
                    .should()
                    .implement(ObservadorCambioEstado.class);
}
