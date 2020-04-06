package ch.yanick.experiments.parsing;

import ch.yanick.experiments.IsoGatewayTestFileLanguage;
import com.intellij.psi.tree.IElementType;

public class ParsingElementType extends IElementType {
    public ParsingElementType(String name) {
        super(name, IsoGatewayTestFileLanguage.INSTANCE);
    }
}
