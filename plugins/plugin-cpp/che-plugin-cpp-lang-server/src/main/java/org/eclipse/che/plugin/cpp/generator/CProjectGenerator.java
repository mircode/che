/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/

package org.eclipse.che.plugin.cpp.generator;

import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.core.ForbiddenException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.api.project.server.FolderEntry;
import org.eclipse.che.api.project.server.handlers.CreateProjectHandler;
import org.eclipse.che.api.project.server.type.AttributeValue;
import org.eclipse.che.plugin.cpp.shared.Constants;

import java.util.Map;

public class CProjectGenerator implements CreateProjectHandler {

    private static final String FILE_NAME = "hello.c";

    @Override
    public void onCreateProject(FolderEntry baseFolder,
                                Map<String, AttributeValue> attributes,
                                Map<String, String> options) throws ForbiddenException, ConflictException, ServerException {
        baseFolder.createFile(FILE_NAME, getClass().getClassLoader().getResourceAsStream("files/default_c_content"));
    }

    @Override
    public String getProjectType() {
        return Constants.C_PROJECT_TYPE_ID;
    }
}

