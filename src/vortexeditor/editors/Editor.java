package vortexeditor.editors;

import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.wst.jsdt.internal.ui.javaeditor.CompilationUnitEditor;

@SuppressWarnings("restriction")
public class Editor
extends CompilationUnitEditor {
	
	/**
	 * Constructor
	 */
	public Editor() {
		
		super();
		
	}
	
	/**
	 * Override setDocumentProvider to inject own DocumentProvider
	 */
	public void setDocumentProvider (IDocumentProvider provider) {
		
		super.setDocumentProvider(new DocumentProvider());
		
	}
	
}