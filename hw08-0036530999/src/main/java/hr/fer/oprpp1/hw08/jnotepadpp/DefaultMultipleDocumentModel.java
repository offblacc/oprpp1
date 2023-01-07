package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
    /**
     * A collection of the documents this model contains.
     */
    private List<SingleDocumentModel> documents = new ArrayList<>();

    /**
     * A reference to the currently selected document.
     */
    private SingleDocumentModel currentDocument;

    /**
     * A collection of listeners that are notified when the currently selected document is changed, a new document is
     * opened or a document is closed.
     */
    private List<MultipleDocumentListener> listeners = new ArrayList<>();

    /**
     * Keeps track of the currently opened tab.
     */
    private int currentTabIndex = 0;


    public DefaultMultipleDocumentModel() {
        super();
        createNewDocument();
        this.addChangeListener(e -> {
            int index = this.getSelectedIndex();
            if (index == -1) {
                this.currentDocument = null;
                return;
            }
            this.currentDocument = this.documents.get(index);
            this.currentTabIndex = index;
            this.notifyListenersCurrentDocumentChanged(null, currentDocument);
        });
    }


    /**
     * {@inheritDoc} This particular implementation returns this, as this class extends {@link JTabbedPane} and is
     * therefore a {@link JComponent}.
     */
    @Override
    public JComponent getVisualComponent() {
        return this;
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        DefaultSingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, null);
        documents.add(newDocument);
        addTab("unnamed", newDocument.getIcon(), newDocument.getTextComponent());
        setIconAt(documents.size() - 1, newDocument.getIcon());
        System.out.println(newDocument.getIcon());
        setSelectedIndex(documents.size() - 1);
        currentDocument = newDocument;
        currentTabIndex = documents.size() - 1;
        listeners.forEach(l -> l.documentAdded(newDocument));
        return newDocument;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        if (path == null) throw new NullPointerException("Path must not be null.");

        SingleDocumentModel newModel = getOpenDocumentAtPath(path);
        if (newModel == null) {
            byte[] data;
            try (InputStream is = this.getClass().getResourceAsStream(path.toString())) {
                if (is == null) throw new IOException();
                data = is.readAllBytes();
            } catch (IOException e) {
                throw new IllegalArgumentException("There was a problem loading the document from the given path.");
            }
            newModel = new DefaultSingleDocumentModel(new String(data), path);
            documents.add(newModel); // add the new document to the collection of documents
            this.addTab(path.toString(), newModel.getTextComponent()); // add an actual tab// TODO the title is not the path, but the name of the file, modify later how you see fit
        }
        currentDocument = newModel;
        for (var l : listeners) l.documentAdded(newModel);
        return newModel;
    }

    /**
     * Checks whether the document on the given path is already opened, returns a reference to it if it is, null
     * otherwise.
     *
     * @param path the path to the document to be checked
     * @return a reference to the document on the given path if it is already opened, null otherwise
     */
    private SingleDocumentModel getOpenDocumentAtPath(Path path) {
        for (SingleDocumentModel document : documents) {
            if (document.getFilePath() != null && document.getFilePath().equals(path)) {
                return document;
            }
        }
        return null;
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {

    }

    @Override
    public void closeDocument(SingleDocumentModel model) { // TODO need to do any checking ?
        documents.remove(model);
        removeTabAt(getIndexOfDocument(model));
        listeners.forEach(l -> l.documentRemoved(model));
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return getTabCount(); // TODO check if this is correct
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    @Override
    public SingleDocumentModel findForPath(Path path) {
        for (SingleDocumentModel document : documents) {
            if (document.getFilePath() != null && document.getFilePath().equals(path)) {
                return document;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc} Returns -1 if the given document is not in the collection of documents.
     *
     * @param doc the document model whose index is to be returned
     * @return the index of the given document model, or -1 if the given document is not in the collection of documents
     */
    @Override
    public int getIndexOfDocument(SingleDocumentModel doc) {
        return documents.indexOf(doc);
    }

    /**
     * Returns an iterator over the documents in this model, delegating to the iterator of the collection of documents.
     *
     * @return an iterator over the documents in this model
     */
    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }

    /**
     * Notifies all the listeners that the currently selected document has changed.
     *
     * @param previousModel the previously selected document, or null if there was no previously selected document
     * @param currentModel  the currently selected document, or null if there is no currently selected document
     */
    private void notifyListenersCurrentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
        listeners.forEach(l -> l.currentDocumentChanged(previousModel, currentModel));
    }

    /**
     * Notifies all the listeners that a new document has been opened.
     *
     * @param model the newly opened document
     */
    private void notifyListenersDocumentAdded(SingleDocumentModel model) {
        listeners.forEach(l -> l.documentAdded(model));
    }

    /**
     * Notifies all the listeners that a document has been closed.
     *
     * @param model the closed document
     */
    private void notifyListenersDocumentRemoved(SingleDocumentModel model) {
        listeners.forEach(l -> l.documentRemoved(model));
    }
}
