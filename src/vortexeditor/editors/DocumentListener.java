package vortexeditor.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class DocumentListener
implements IDocumentListener {
	
	/**
	 * Constructor
	 */
	public DocumentListener() {
		
	}
	
	/**
	 * Document changed event handler
	 * 
	 * @param event Event information
	 */
	@Override
	public void documentChanged(DocumentEvent event) {
		
		final IEditorPart activeEditor=PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow()
			.getActivePage()
			.getActiveEditor();
		
		IFile file = (IFile)activeEditor.getEditorInput()
			.getAdapter(IFile.class);
		
		Server.documentChanged(event,file);
		
	}
	
	/**
	 * Document about to be changed event handler
	 * 
	 * @param event Event information
	 */
	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {
		
	}
	
}