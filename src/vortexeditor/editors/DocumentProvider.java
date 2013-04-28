package vortexeditor.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.wst.jsdt.internal.ui.javaeditor.CompilationUnitDocumentProvider;

@SuppressWarnings("restriction")
public class DocumentProvider 
extends CompilationUnitDocumentProvider {
	
	/**
	 * Connect document
	 * 
	 * @param element Element
	 */
	public void connect(Object element) throws CoreException {
		
		super.connect(element);
		
		IDocument document=this.getDocument(element);
		
		if (document!=null) {
			
			document.addDocumentListener(new DocumentListener());
			
			Server.documentOpened(document);
			
		}
		
	}
	
	/**
	 * Disconnect document
	 * 
	 * @param element Element
	 */
	public void disconnect(Object element) {
		
		super.disconnect(element);
		
		IDocument document=this.getDocument(element);
		
		Server.documentClosed(document);
		
	}
	
}