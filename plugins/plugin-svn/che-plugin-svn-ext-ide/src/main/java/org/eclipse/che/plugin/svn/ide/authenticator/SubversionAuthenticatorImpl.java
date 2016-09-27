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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import org.eclipse.che.api.promises.client.Operation;
import org.eclipse.che.api.promises.client.OperationException;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.callback.AsyncPromiseHelper;
import org.eclipse.che.ide.api.oauth.OAuth2Authenticator;
import org.eclipse.che.plugin.svn.ide.SubversionClientService;
import org.eclipse.che.security.oauth.OAuthCallback;
import org.eclipse.che.security.oauth.OAuthStatus;

import javax.validation.constraints.NotNull;

import static org.eclipse.che.ide.util.StringUtils.isNullOrEmpty;

/**
 * @author Igor Vinokur
 */
public class SubversionAuthenticatorImpl implements OAuth2Authenticator, OAuthCallback, SubversionAuthenticatorViewImpl.ActionDelegate {


    private static final String SVN = "svn";

    private AsyncCallback<OAuthStatus> callback;

    private final SubversionAuthenticatorView view;
    private final SubversionClientService     clientService;

    private String authenticationUrl;

    @Inject
    public SubversionAuthenticatorImpl(SubversionAuthenticatorView view,
                                       SubversionClientService clientService) {
        this.view = view;
        this.clientService = clientService;
        this.view.setDelegate(this);
    }

    @Override
    public void authenticate(String authenticationUrl, @NotNull final AsyncCallback<OAuthStatus> callback) {
        this.authenticationUrl = authenticationUrl;
        this.callback = callback;
        view.cleanCredentials();
        view.showDialog();
    }

    @Override
    public Promise<OAuthStatus> authenticate(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;

        return AsyncPromiseHelper.createFromAsyncRequest(new AsyncPromiseHelper.RequestCall<OAuthStatus>() {
            @Override
            public void makeCall(AsyncCallback<OAuthStatus> callback) {
                SubversionAuthenticatorImpl.this.callback = callback;
                view.showDialog();
            }
        });
    }

    @Override
    public String getProviderName() {
        return SVN;
    }

    @Override
    public void onCancelClicked() {
        callback.onFailure(new Exception("Authorization request rejected by user."));
        view.closeDialog();
    }

    @Override
    public void onLogInClicked() {
        clientService.saveCredentials(authenticationUrl, view.getUserName(), view.getPassword()).then(new Operation<Void>() {
            @Override
            public void apply(Void arg) throws OperationException {
                onAuthenticated(OAuthStatus.fromValue(3));
            }
        });
        view.closeDialog();
    }

    @Override
    public void onCredentialsChanged() {
        view.setEnabledLogInButton(!isNullOrEmpty(view.getUserName()) && !isNullOrEmpty(view.getPassword()));
    }

    @Override
    public void onAuthenticated(OAuthStatus authStatus) {
        callback.onSuccess(authStatus);
    }
}
