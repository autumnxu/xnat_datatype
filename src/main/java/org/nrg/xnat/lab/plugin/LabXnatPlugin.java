/*
 * xnat-lab: org.nrg.xnat.lab.plugin.LabXnatPlugin
 * XNAT http://www.xnat.org
 * Copyright (c) 2017, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.xnat.lab.plugin;

import org.nrg.framework.annotations.XnatDataModel;
import org.nrg.framework.annotations.XnatPlugin;
import org.nrg.xdat.bean.LabEdemasamplecollectionBean;
import org.nrg.xdat.bean.RadRadiologyreaddataBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@XnatPlugin(value = "labPlugin", name = "XNAT 1.7 Lab 2016 Plugin", entityPackages = "org.nrg.xnat.lab.entities",
            dataModels = {@XnatDataModel(value = LabEdemasamplecollectionBean.SCHEMA_ELEMENT_NAME,
                                         singular = "Stroke Edema Collection", // Edemasample Collection
                                         plural = "Stroke Edema Collections"),
                          @XnatDataModel(value = RadRadiologyreaddataBean.SCHEMA_ELEMENT_NAME,
                                         singular = "Radiology Read",
                                         plural = "Radiology Reads")})
@ComponentScan({"org.nrg.xnat.lab.subjectmapping.preferences",
        "org.nrg.xnat.lab.subjectmapping.repositories",
        "org.nrg.xnat.lab.subjectmapping.rest",
        "org.nrg.xnat.lab.subjectmapping.services.impl"})
public class LabXnatPlugin {
    @Bean
    public String labPluginMessage() {
        return "Hello there from the lab plugin!";
    }
}
