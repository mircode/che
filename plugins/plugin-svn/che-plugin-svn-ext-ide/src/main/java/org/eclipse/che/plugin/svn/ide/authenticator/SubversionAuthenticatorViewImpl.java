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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import org.eclipse.che.ide.ui.window.Window;
import org.eclipse.che.plugin.svn.ide.SubversionExtensionLocalizationConstants;

/**
 * @author Igor Vinokur
 */
public class SubversionAuthenticatorViewImpl extends Window implements SubversionAuthenticatorView {

    interface SubversionAuthenticatorImplUiBinder extends UiBinder<Widget, SubversionAuthenticatorViewImpl> {
    }

    private static SubversionAuthenticatorImplUiBinder uiBinder = GWT.create(SubversionAuthenticatorImplUiBinder.class);

    private SubversionExtensionLocalizationConstants locale;
    private ActionDelegate                           delegate;

    @UiField
    TextBox userNameTextBox;
    @UiField
    TextBox passwordTextBox;

    private final Button acceptButton;

    @Inject
    public SubversionAuthenticatorViewImpl(SubversionExtensionLocalizationConstants locale) {
        Widget widget = uiBinder.createAndBindUi(this);
        this.setWidget(widget);
        this.setTitle(locale.cleanupTitle());
        acceptButton = createButton("Log In", "svn-authenticate-login", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onAccepted();
            }
        });
        Button cancelButton = createButton("Cancel", "debugId", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelled();
            }
        });

        userNameTextBox.addDomHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                delegate.onCredentialsChanged();
            }
        }, KeyPressEvent.getType());
        passwordTextBox.addDomHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                delegate.onCredentialsChanged();
            }
        }, KeyPressEvent.getType());
        addButtonToFooter(acceptButton);
        addButtonToFooter(cancelButton);
    }

    @Override
    public void showDialog() {
        super.show();
    }

    @Override
    public String getUserName() {
        return userNameTextBox.getText();
    }

    @Override
    public String getPassword() {
        return passwordTextBox.getText();
    }

    @Override
    public void cleanCredentials() {
        userNameTextBox.setText("");
        passwordTextBox.setText("");
        setEnabledLogInButton(false);
    }

//    @UiHandler({"userNameTextBox", "passwordTextBox"})
//    void credentialChangeHandler(final ValueChangeEvent<String> event) {
//        delegate.onCredentialsChanged();
//    }

    @Override
    public void setEnabledLogInButton(boolean enabled) {
        acceptButton.setEnabled(enabled);
    }

    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }


}
