package io.jenkins.plugins.coverage.model;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import hudson.model.Run;
import hudson.util.TextFile;

/**
 * Server side model that provides the data for the source code view of the coverage results. The layout of the
 * associated view is defined corresponding jelly view 'index.jelly'.
 *
 * @author Ullrich Hafner
 */
public class SourceViewModel extends CoverageViewModel {
    /**
     * Creates a new source view model instance.
     *
     * @param owner
     *         the owner of this view
     * @param fileNode
     *         the selected file node of the coverage tree
     */
    public SourceViewModel(final Run<?, ?> owner, final CoverageNode fileNode) {
        super(owner, fileNode);
    }

    /**
     * Returns the source file rendered in HTML.
     *
     * @return {@code true} if the source file is available, {@code false} otherwise
     */
    public String getSourceFileContent() {
        try {
            File rootDir = getOwner().getRootDir();
            if (isSourceFileInNewFormatAvailable()) {
                return new SourceCodeFacade().read(rootDir, getId(), getNode().getPath());
            }
            if (isSourceFileInOldFormatAvailable()) {
                return new TextFile(getFileForBuildsWithOldVersion(rootDir, getNode().getName())).read(); // fallback with sources persisted using the < 2.1.0 serialization
            }
            return Messages.Coverage_Not_Available();
        }
        catch (IOException | InterruptedException exception) {
            return ExceptionUtils.getStackTrace(exception);
        }
    }
}
