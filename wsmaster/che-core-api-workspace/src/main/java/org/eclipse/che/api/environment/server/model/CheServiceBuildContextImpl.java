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
package org.eclipse.che.api.environment.server.model;

import java.util.Objects;

/**
 * Describes how to build image for container creation.
 *
 * @author Alexander Garagatyi
 */
public class CheServiceBuildContextImpl {
    private String context;
    private String dockerfilePath;
    private String dockerfileContent;

    public CheServiceBuildContextImpl() {}

    public CheServiceBuildContextImpl(String context,
                                      String dockerfilePath,
                                      String dockerfileContent) {
        this.context = context;
        this.dockerfilePath = dockerfilePath;
        this.dockerfileContent = dockerfileContent;
    }

    public CheServiceBuildContextImpl(CheServiceBuildContextImpl buildContext) {
        this.context = buildContext.getContext();
        this.dockerfilePath = buildContext.getDockerfilePath();
        this.dockerfileContent = buildContext.getDockerfileContent();
    }

    /**
     * Build context.
     *
     * <p/>Can be git repository, url to Dockerfile.
     */
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public CheServiceBuildContextImpl withContext(String context) {
        this.context = context;
        return this;
    }

    /**
     * Path to alternate Dockerfile, including name.
     *
     * <p/> Needed if dockerfile has non-default name or is not placed in the root of build context.
     * <br/> Mutually exclusive with {@code #getDockerfileContent()}.
     */
    public String getDockerfilePath() {
        return dockerfilePath;
    }

    public void setDockerfilePath(String dockerfilePath) {
        this.dockerfilePath = dockerfilePath;
    }

    public CheServiceBuildContextImpl withDockerfilePath(String dockerfilePath) {
        this.dockerfilePath = dockerfilePath;
        return this;
    }

    /**
     * Content of Dockerfile.
     *
     * <p/> Mutually exclusive with {@code #getDockerfilePath()}.
     */
    public String getDockerfileContent() {
        return dockerfileContent;
    }

    public void setDockerfileContent(String dockerfileContent) {
        this.dockerfileContent = dockerfileContent;
    }

    public CheServiceBuildContextImpl withDockerfileContent(String dockerfileContent) {
        this.dockerfileContent = dockerfileContent;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheServiceBuildContextImpl)) return false;
        CheServiceBuildContextImpl that = (CheServiceBuildContextImpl)o;
        return Objects.equals(context, that.context) &&
               Objects.equals(dockerfilePath, that.dockerfilePath) &&
               Objects.equals(dockerfileContent, that.dockerfileContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context, dockerfilePath, dockerfileContent);
    }

    @Override
    public String toString() {
        return "CheServiceBuildContextImpl{" +
               "context='" + context + '\'' +
               ", dockerfilePath='" + dockerfilePath + '\'' +
               ", dockerfileContent='" + dockerfileContent + '\'' +
               '}';
    }
}
