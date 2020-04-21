
/**
 * @author hosseinmp76
 *
 */
module ir.hosseinmp76.workFlowPlanner {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires transitive javafx.graphics;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires java.activation;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.aop;

    requires java.desktop;

    requires java.xml;
    
    requires java.xml.bind;

//    requires com.fasterxml.classmate;
//    requires org.apache.logging.log4j.slf4j;
    requires org.apache.logging.log4j;
    requires commons.logging;
//    requires org.apache.logging.log4j.jcl;
//    requires org.slf4j;
    requires org.apache.logging.log4j.core;

    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires java.sql;

    requires org.hibernate.validator;
    requires org.hibernate.orm.jcache;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires net.bytebuddy;
    requires java.naming;
    requires javax.el;
    requires com.zaxxer.hikari;
    requires org.hibernate.orm.hikaricp;

    requires java.persistence;

    requires javax.inject;

    requires static lombok;
    requires java.annotation;
    requires commons.math3;
    requires java.validation;
    requires commons.csv;

    opens ir.hosseinmp76.workFlowPlanner.ui to javafx.fxml;
    opens ir.hosseinmp76.workFlowPlanner.persistency to spring.core;
    opens ir.hosseinmp76.workFlowPlanner.logic to spring.core;
    opens ir.hosseinmp76.workFlowPlanner.model to spring.core;

    exports ir.hosseinmp76.workFlowPlanner.ui;
    exports ir.hosseinmp76.workFlowPlanner.model;
    exports ir.hosseinmp76.workFlowPlanner.logic;
    exports ir.hosseinmp76.workFlowPlanner.persistency;
    exports ir.hosseinmp76.workFlowPlanner.utills;

}