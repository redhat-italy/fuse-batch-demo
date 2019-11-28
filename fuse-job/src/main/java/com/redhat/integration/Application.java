/*
 * Copyright 2005-2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.redhat.integration;

import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
         /*
    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
                new CamelHttpTransportServlet(), "/batch-url/*");
        servlet.setName("CamelServlet");
        return servlet;
    }  */

    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {
            
            // @formatter:off
            onException(Exception.class)
                    .to("file:{{destination.path}}export?fileName=${date:now:yyyyMMdd-HH_mm_ss}-error.csv")
                    .log("--->  ${body}").end()
                    .setHeader("CamelHttpMethod",simple("POST")).to("http://localhost:8081/shutdown");
            
            CsvDataFormat csv = new CsvDataFormat();

            from("timer://simpleTimer?repeatCount=1")
                .setBody(simple("{{destination.path}}"))
                .to("sql:select * from orders order by product")
                    .marshal(csv)
                    .to("file:{{destination.path}}export?fileName=${date:now:yyyyMMdd-HH_mm_ss}-export.csv")
            .log("--->  ${body}").end()
            .setHeader("CamelHttpMethod",simple("POST")).to("http://localhost:8081/shutdown");



            // @formatter:on
        }
    }
}