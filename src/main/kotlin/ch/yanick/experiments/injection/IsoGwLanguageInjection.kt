package ch.yanick.experiments.injection

import ch.yanick.experiments.IsoGatewayTestFileLanguage
import ch.yanick.experiments.parsing.psi.MessageElementType
import com.intellij.json.JsonLanguage
import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.TextRange
import com.intellij.psi.InjectedLanguagePlaces
import com.intellij.psi.LanguageInjector
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.util.elementType
import groovy.util.logging.Slf4j

@Slf4j
class IsoGwLanguageInjection : LanguageInjector {
    companion object {
        private val logger = Logger.getInstance(IsoGwLanguageInjection::class.java)
    }

    override fun getLanguagesToInject(
        host: PsiLanguageInjectionHost,
        injectionPlacesRegistrar: InjectedLanguagePlaces
    ) {
        val file = host.containingFile ?: return
        val language = file.language

        if(language != IsoGatewayTestFileLanguage) {
            return
        }

        if(host.elementType !is MessageElementType) {
            return
        }

        val elemType = host.elementType as MessageElementType

        if(elemType.uri.contains("iso:20022", true)) {
            injectionPlacesRegistrar.addPlace(XMLLanguage.INSTANCE, TextRange.from(0, host.textLength), null, null)
        } else if(elemType.uri.contains("json", true)) {
            injectionPlacesRegistrar.addPlace(JsonLanguage.INSTANCE, TextRange.from(0, host.textLength), null, null)
        }
    }

}