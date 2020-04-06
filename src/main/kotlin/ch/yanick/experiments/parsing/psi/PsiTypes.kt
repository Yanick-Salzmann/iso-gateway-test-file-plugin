package ch.yanick.experiments.parsing.psi

import ch.yanick.experiments.IsoGatewayTestFileLanguage
import ch.yanick.experiments.IsoGatewayTestFileType
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.stubs.StubElement

interface MetaKeyElementStub : StubElement<MetaKeyElement>
interface MessageElementStub : StubElement<MessageElement>
interface MetadataElementStub : StubElement<MetadataElement>
interface RootElementStub : StubElement<RootElement>

class MetaKeyElement(node: ASTNode) : StubBasedPsiElementBase<MetaKeyElementStub>(node)

class MessageElement(node: ASTNode) : StubBasedPsiElementBase<MessageElementStub>(node), PsiLanguageInjectionHost {
    val type: MessageElementType = node.elementType as MessageElementType

    override fun updateText(text: String): PsiLanguageInjectionHost = this

    override fun createLiteralTextEscaper() = LiteralTextEscaper.createSimple(this)

    override fun isValidHost() = true
}

class MetadataElement(node: ASTNode) : StubBasedPsiElementBase<MetadataElementStub>(node)
class RootElement(node: ASTNode) : StubBasedPsiElementBase<RootElementStub>(node)

class IsoGwTestFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, IsoGatewayTestFileLanguage) {
    override fun getFileType(): FileType = IsoGatewayTestFileType.INSTANCE
}