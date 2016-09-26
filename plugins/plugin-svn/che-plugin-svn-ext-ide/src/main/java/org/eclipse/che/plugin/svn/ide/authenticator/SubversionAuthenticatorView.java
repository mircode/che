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
package org.eclipse.che.plugin.svn.ide.authenticator;

import com.google.inject.ImplementedBy;

import org.eclipse.che.ide.api.mvp.View;

/**
 * @author Roman Nikitenko
 */
@ImplementedBy(SubversionAuthenticatorViewImpl.class)
public interface SubversionAuthenticatorView extends View<SubversionAuthenticatorView.ActionDelegate> {

    interface ActionDelegate {

        /** Defines what's done when the user clicks cancel. */
        void onCancelled();
        /** Defines what's done when the user clicks OK. */
        void onAccepted();
        void onCredentialsChanged();

    }
    String getUserName();

    String getPassword();

    /**
     * Clean userNameTextBox and passwordTextBox fields
     */
    void cleanCredentials();

    void setEnabledLogInButton(boolean enabled);

    /** Show dialog. */
    void showDialog();
}
