package DMCBM.StatBlocks.Builder;

import j2html.tags.ContainerTag;
import j2html.tags.DomContent;

public class BonusTags {

    private BonusTags() {
    }

    public static ContainerTag stat_block() {
        return new ContainerTag("stat-block");
    }

    public static ContainerTag stat_block(String text) {
        return new ContainerTag("stat-block").withText(text);
    }

    public static ContainerTag stat_block(DomContent... dc) {
        return new ContainerTag("stat-block").with(dc);
    }

    public static ContainerTag creature_heading() {
        return new ContainerTag("creature-heading");
    }

    public static ContainerTag creature_heading(String text) {
        return new ContainerTag("creature-heading").withText(text);
    }

    public static ContainerTag creature_heading(DomContent... dc) {
        return new ContainerTag("creature-heading").with(dc);
    }

    public static ContainerTag top_stats() {
        return new ContainerTag("top-stats");
    }

    public static ContainerTag top_stats(String text) {
        return new ContainerTag("top-stats").withText(text);
    }

    public static ContainerTag top_stats(DomContent... dc) {
        return new ContainerTag("top-stats").with(dc);
    }

    public static ContainerTag property_line() {
        return new ContainerTag("property-line");
    }

    public static ContainerTag property_line(String text) {
        return new ContainerTag("property-line").withText(text);
    }

    public static ContainerTag property_line(DomContent... dc) {
        return new ContainerTag("property-line").with(dc);
    }

    public static ContainerTag abilities_block() {
        return new ContainerTag("abilities-block");
    }

    public static ContainerTag abilities_block(String text) {
        return new ContainerTag("abilities-block").withText(text);
    }

    public static ContainerTag abilities_block(DomContent... dc) {
        return new ContainerTag("abilities-block").with(dc);
    }

    public static ContainerTag property_block() {
        return new ContainerTag("property-block");
    }

    public static ContainerTag property_block(String text) {
        return new ContainerTag("property-block").withText(text);
    }

    public static ContainerTag property_block(DomContent... dc) {
        return new ContainerTag("property-block").with(dc);
    }
}