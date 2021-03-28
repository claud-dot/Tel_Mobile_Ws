/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author ASUS
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(header.TelMobCrossOrigins.class);
        resources.add(service.AcceuilleResource.class);
        resources.add(service.AppelResource.class);
        resources.add(service.CategorieResource.class);
        resources.add(service.ClientsResource.class);
        resources.add(service.DebitResource.class);
        resources.add(service.DepotResource.class);
        resources.add(service.OffreResource.class);
    }
    
}
