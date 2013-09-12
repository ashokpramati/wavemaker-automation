package com.pramati.wavemaker.pages;

import com.pramati.wavemaker.page.BasePage;

public class HomePage extends BasePage {

	private static final String CREATE_JOIN_SESSION_ID = "button_change_environment";
	private static final String DEBUGGER_OPTIONS_ID = "button_debugger_options";
	private static final String TOKEN_EDITOR_ID = "button_token_editor";

	private static final String CONNECT_TO_SESSION_ID = "button_connect";
	private static final String CHANGE_TO_WEBRTC_STACK_ID = "redirect_to_webrtc";

	public HomePage() {
		super.init();
	}

	public HomePage(BasePage basePage) {
		waitForElementLocatedByID(CONNECT_TO_SESSION_ID, getTimeOutInSeconds());
		waitForElementLocatedByID(CHANGE_TO_WEBRTC_STACK_ID,
				getTimeOutInSeconds());

		this.validate();
	}

	/**
	 * Validate that we are indeed in the tokbox debugger home page
	 */
	private void validate() {
		assertTitle("Tester");

		assertText("Create/Join Session");
		assertText("Debugger Options");
		assertText("Token Editor");
	}

	public void clickCreateJoinSession() {
		clickByID(CREATE_JOIN_SESSION_ID);
		waitForElementLocatedByID("environment_options", getTimeOutInSeconds());
	}

	public void clickDebuggerOptions() {
		clickByID(DEBUGGER_OPTIONS_ID);
	}

	public void clickTokenEditor() {
		clickByID(TOKEN_EDITOR_ID);
	}

	public void clickConnectToSession() {
		clickByID(CONNECT_TO_SESSION_ID);
	}

	public void clickChangeToWebRTCStack() {
		clickByID(CHANGE_TO_WEBRTC_STACK_ID);
	}
}

