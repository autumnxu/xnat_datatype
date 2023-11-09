/*
 * xnat-lab: org.apache.turbine.app.xnat.modules.screens.XDATScreen_edit_lab_edemaMarker
 * XNAT http://www.xnat.org
 * Copyright (c) 2017, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.apache.turbine.app.xnat.modules.screens;

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.nrg.xdat.XDAT;
import org.nrg.xdat.om.LabEdemasamplecollection;
import org.nrg.xdat.om.XnatSubjectassessordata;
import org.nrg.xdat.om.XnatSubjectdata;
import org.nrg.xft.XFTItem;
import org.nrg.xft.exception.ElementNotFoundException;
import org.nrg.xft.exception.FieldNotFoundException;
import org.nrg.xft.exception.XFTInitException;
import org.nrg.xnat.turbine.modules.screens.EditSubjectAssessorScreen;

import java.util.List;

public class XDATScreen_edit_lab_edemasampleCollection extends EditSubjectAssessorScreen {
    @Override
    public String getElementName() {
        return LabEdemasamplecollection.SCHEMA_ELEMENT_NAME;
    }

    @Override
    public void finalProcessing(RunData data, Context context) {
        super.finalProcessing(data,context);

        try {
            final String subjectId;
            if (!context.containsKey("subjectId")) {
                if (context.containsKey("part")) {
                    final Object part = context.get("part");
                    if (part instanceof XnatSubjectdata) {
                        subjectId = ((XnatSubjectdata) part).getId();
                    } else if (part instanceof XFTItem) {
                        subjectId = ((XFTItem) part).getStringProperty("ID");
                    } else {
                        subjectId = part.toString();
                    }
                    context.put("subjectId", subjectId);
                } else {
                    subjectId = (String) context.get("part_id");
                }
            } else {
                subjectId = (String) context.get("subjectId");
            }

            if (!context.containsKey("label")) {
                final XnatSubjectdata               subject    = XnatSubjectdata.getXnatSubjectdatasById(subjectId, XDAT.getUserDetails(), false);
                final List<XnatSubjectassessordata> edemasamples = subject.getExperiments_experiment(LabEdemasamplecollection.SCHEMA_ELEMENT_NAME);
                final String subjectLabel = subject.getLabel();
                int index = 1;
                String label = null;
                while (label == null) {
                    final String test = subjectLabel + "_BIO" + String.format("%02d", index);
                    boolean matches = false;
                    for (final XnatSubjectassessordata edemasample : edemasamples) {
                        if (edemasample.getLabel().equals(test)) {
                            matches = true;
                            index++;
                            break;
                        }
                    }
                    if (!matches) {
                        label = test;
                    }
                }
                context.put("label", label);
            }
        } catch (XFTInitException | ElementNotFoundException | FieldNotFoundException e) {
            final String message = "An error occurred trying to get the subject ID when creating a edema stroke collection assessor.";
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }
}
