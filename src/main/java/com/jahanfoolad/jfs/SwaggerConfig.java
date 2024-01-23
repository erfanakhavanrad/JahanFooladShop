//package com.jahanfoolad.jfs;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.hateoas.client.LinkDiscoverer;
//import org.springframework.hateoas.client.LinkDiscoverers;
//import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
//import org.springframework.plugin.core.SimplePluginRegistry;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.VendorExtension;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//    Contact contact = new Contact("Jahan Foolad Iraninan", "https://jahanfooladiranian.com/", "info@jahanfooladiranian.com");
//    List<VendorExtension> vendorExtensions = new ArrayList<>();
//
//    ApiInfo apiInfo = new ApiInfo("AhanSea and MyAhan Restful Web Services Documentation",
//            "This page documents User Management RESTful Web Service endpoints",
//            "1.0",
//            "https://jahanfooladiranian.com/%d9%82%d9%88%d8%a7%d9%86%db%8c%d9%86-%d9%88-%d9%85%d9%82%d8%b1%d8%b1%d8%a7%d8%aa/",
//            contact,
//            "Apache 2.0",
//            "http://www.apache.org/licenses/LICENSE-2.0",
//            vendorExtensions
//    );
//
//    @Bean
//    public Docket apiDocket() {
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPs")))
//                .apiInfo(apiInfo)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.jahanfoolad.jfs"))
////                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//        return docket;
//    }
//
//    @Bean
//    public LinkDiscoverers discoverers() {
//        List<LinkDiscoverer> plugins = new ArrayList<>();
//        plugins.add(new CollectionJsonLinkDiscoverer());
//        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
//    }
//
//}
