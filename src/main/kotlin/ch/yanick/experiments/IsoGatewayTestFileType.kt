package ch.yanick.experiments

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader

class IsoGatewayTestFileType : LanguageFileType(IsoGatewayTestFileLanguage) {
    companion object {
        public val INSTANCE = IsoGatewayTestFileType()
    }

    private val languageIcon = IconLoader.getIcon("/icons/iso-gw-language-icon.png")

    override fun getIcon() = languageIcon

    override fun getName() = IsoGatewayTestFileLanguage.displayName

    override fun getDefaultExtension() = "igwt"

    override fun getDescription() = "Language for regression test files for the ISO gateway"

}