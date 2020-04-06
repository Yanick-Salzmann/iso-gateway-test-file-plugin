package ch.yanick.experiments

import com.intellij.lang.Language

const val ISO_GATEWAY_LANGUAGE_ID = "iso-gateway-test-file-language-id"

object IsoGatewayTestFileLanguage : Language(ISO_GATEWAY_LANGUAGE_ID) {
    override fun getDisplayName() = "ISO Gateway Test File"
}