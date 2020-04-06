package ch.yanick.experiments.parsing.psi

import ch.yanick.experiments.IsoGatewayTestFileLanguage
import com.intellij.psi.tree.IElementType

class IsoGatewayRootType : IElementType("iso-gw-root", IsoGatewayTestFileLanguage)
class MessageElementType(val uri: String) : IElementType("message-target", IsoGatewayTestFileLanguage)
class MetadataElementType: IElementType("metadata-target", IsoGatewayTestFileLanguage)