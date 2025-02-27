package io.jenkins.plugins.coverage.model.visualization.dashboard;

import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.math.Fraction;

import io.jenkins.plugins.coverage.model.CoverageBuildAction;
import io.jenkins.plugins.coverage.model.CoverageMetric;
import io.jenkins.plugins.coverage.model.Messages;
import io.jenkins.plugins.coverage.model.util.FractionFormatter;
import io.jenkins.plugins.coverage.model.visualization.colorization.ColorProvider.DisplayColors;
import io.jenkins.plugins.coverage.model.visualization.colorization.CoverageLevel;

/**
 * Concrete implementation of {@link CoverageColumnType} which represents the project coverage.
 *
 * @author Florian Orendi
 */
public class ProjectCoverage extends CoverageColumnType {

    /**
     * Creates a column type to be used for representing the project coverage.
     */
    public ProjectCoverage() {
        super(Messages._Project_Coverage_Type());
    }

    @Override
    public Optional<Fraction> getCoverage(final CoverageBuildAction action, final CoverageMetric metric) {
        if (action.hasCoverage(metric)) {
            return Optional.of(action
                    .getCoverage(metric)
                    .getCoveredPercentage());
        }
        return Optional.empty();
    }

    @Override
    public DisplayColors getDisplayColors(final Fraction coverage) {
        return CoverageLevel.getDisplayColorsOfCoverageLevel(coverage.doubleValue(), getColorProvider());
    }

    @Override
    public String formatCoverage(final Fraction coverage, final Locale locale) {
        return FractionFormatter.formatPercentage(coverage, locale);
    }
}
