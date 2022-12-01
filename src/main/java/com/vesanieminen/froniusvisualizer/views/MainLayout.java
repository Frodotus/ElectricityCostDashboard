package com.vesanieminen.froniusvisualizer.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vesanieminen.froniusvisualizer.components.MaterialIcon;
import com.vesanieminen.froniusvisualizer.components.appnav.AppNav;
import com.vesanieminen.froniusvisualizer.components.appnav.AppNavItem;
import com.vesanieminen.froniusvisualizer.util.css.FontFamily;

import javax.servlet.http.Cookie;

import static com.vaadin.flow.component.html.AnchorTarget.BLANK;
import static com.vesanieminen.froniusvisualizer.util.Utils.enLocale;
import static com.vesanieminen.froniusvisualizer.util.Utils.fiLocale;

public class MainLayout extends AppLayout {

    public static final String URL_SUFFIX = " ⋅ Liukuri";

    private H1 title;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);

        // App header
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        title = new H1();
        title.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        Header header = new Header(toggle, title);
        header.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL,
                LumoUtility.Padding.SMALL);

        addToNavbar(true, header);

        // Navigation sidebar
        Span icon = MaterialIcon.OFFLINE_BOLT.create(LumoUtility.IconSize.LARGE, LumoUtility.TextColor.PRIMARY);

        Span name = new Span("LIUKURI");
        name.addClassNames(FontFamily.LOGO, LumoUtility.FontSize.XXLARGE, LumoUtility.TextColor.PRIMARY);

        Div app = new Div(icon, name);
        app.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Gap.XSMALL, LumoUtility.Padding.MEDIUM);

        AppNav nav = new AppNav();
        nav.addClassNames(LumoUtility.Padding.Horizontal.SMALL);
        nav.addItem(new AppNavItem(getTranslation("Chart"), NordpoolspotView.class, MaterialIcon.TIMELINE));
        nav.addItem(new AppNavItem(getTranslation("List"), PriceListView.class, MaterialIcon.LIST));
        nav.addItem(new AppNavItem(getTranslation("Calculator"), PriceCalculatorView.class, MaterialIcon.CALCULATE));

        // Vaadin link
        final var vaadinIcon = new Icon(VaadinIcon.VAADIN_H);
        vaadinIcon.addClassNames(LumoUtility.TextColor.PRIMARY, LumoUtility.Margin.Left.AUTO);
        final var vaadinLink = new Anchor("http://vaadin.com", getTranslation("Built with Vaadin"), BLANK);
        vaadinLink.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER);
        vaadinLink.add(vaadinIcon);

        // GitHub link
        final var githubIcon = new Image("images/GitHub-Mark-32px.png", "GitHub icon");
        githubIcon.addClassNames("footer-icon");
        final var githubLink = new Anchor("https://github.com/vesanieminen/ElectricityCostDashboard", getTranslation("Fork me on GitHub"));
        githubLink.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER);
        githubLink.add(githubIcon);
        githubIcon.addClassNames(LumoUtility.IconSize.MEDIUM, LumoUtility.TextColor.PRIMARY, LumoUtility.Margin.Left.AUTO);

        final var discordIcon = new Image("icons/discord-mark-black.png", "Discord icon");
        discordIcon.addClassNames("footer-icon");
        final var discordLink = new Anchor("https://discord.com/invite/WHcY2UkWVp", getTranslation("Join Discord"));
        discordLink.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER);
        discordLink.add(discordIcon);
        discordIcon.addClassNames(LumoUtility.IconSize.MEDIUM, LumoUtility.TextColor.PRIMARY, LumoUtility.Margin.Left.AUTO);

        var links = new Div(vaadinLink, githubLink, discordLink);
        links.addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.Padding.Horizontal.MEDIUM);
        links.addClassNames(LumoUtility.Gap.MEDIUM, LumoUtility.Margin.Top.XLARGE);

        addToDrawer(app, nav, links);
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        title.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : getTranslation(title.value().replace(URL_SUFFIX, ""));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        // Ko-Fi link
        final var image = new Image("https://cdn.ko-fi.com/cdn/kofi2.png?v=3", getTranslation("Buy Me a Coffee at ko-fi.com"));
        image.setHeight("36px");
        image.getStyle().set("border", "0px");
        image.getElement().setAttribute("height", "36");
        image.getElement().setAttribute("border", "0");
        final var kofiLink = new Anchor("https://ko-fi.com/F2F4FU50T");
        kofiLink.setTarget(BLANK);
        kofiLink.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER);
        kofiLink.add(image);

        final var navbarContent = new Div(kofiLink, createChangeLanguageButton(attachEvent));
        navbarContent.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.XSMALL, LumoUtility.Margin.Left.AUTO);

        addToNavbar(true, navbarContent);
    }

    private Button createChangeLanguageButton(AttachEvent attachEvent) {
        var changeLanguage = createButton();
        changeLanguage.setSizeUndefined();
        changeLanguage.addClassNames(LumoUtility.Padding.Horizontal.MEDIUM);
        updateChangeLanguageButtonIcon(attachEvent.getUI(), changeLanguage);
        changeLanguage.addClickListener(e -> {
            VaadinService.getCurrentResponse().addCookie(new Cookie("locale", fiLocale.equals(attachEvent.getUI().getLocale()) ? enLocale.toLanguageTag() : fiLocale.toLanguageTag()));
            updateChangeLanguageButtonIcon(attachEvent.getUI(), changeLanguage);
            attachEvent.getUI().getPage().reload();
        });
        return changeLanguage;
    }

    private static Button createButton() {
        Button button = new Button();
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button.setWidthFull();
        button.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.BorderRadius.NONE, LumoUtility.Margin.Vertical.NONE, LumoUtility.Height.MEDIUM);
        return button;
    }

    private void updateChangeLanguageButtonIcon(UI ui, Button changeLanguage) {
        //changeLanguage.setText(fiLocale.equals(ui.getLocale()) ? getTranslation("to.english") : getTranslation("to.finnish"));
        if (fiLocale.equals(ui.getLocale())) {
            final var finnishIcon = new Image("icons/finland.png", "Finnish");
            finnishIcon.getElement().setAttribute("height", "32");
            finnishIcon.getElement().setAttribute("width", "32");
            changeLanguage.setIcon(finnishIcon);
        } else {
            final var ukIcon = new Image("icons/united-kingdom.png", "English");
            ukIcon.getElement().setAttribute("height", "32");
            ukIcon.getElement().setAttribute("width", "32");
            changeLanguage.setIcon(ukIcon);
        }
    }
}
