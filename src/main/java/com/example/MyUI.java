package com.example;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.io.File;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.example.MyAppWidgetset")
@Push
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        final HorizontalLayout image_layout=new HorizontalLayout();

        final Button button1 = new Button("Sample");

        /* Find the application directory */
        String basepath;
        basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();

        // Image as a file resource

        FileResource resource1 = new FileResource(new File(basepath +
                "/VAADIN/thumb_IMG_0172_1024.jpg"));
        FileResource resource2 = new FileResource(new File(basepath +
                "/VAADIN/thumb_IMG_0174_1024.jpg"));

        FileResource resource3 = new FileResource(new File(basepath +
                "/VAADIN/thumb_IMG_0175_1024.jpg"));
        FileResource resource4 = new FileResource(new File(basepath +
                "/VAADIN/thumb_IMG_0195_1024.jpg"));

        // Show the image in the application
        Image image1 = new Image("Image from file", resource1);
        image1.setWidth("400px");
        image1.setHeight("300px");
        image1.setVisible(false);

        Image image2 = new Image("Image from file", resource2);
        image2.setWidth("400px");
        image2.setHeight("300px");
        image2.setVisible(true);

        Image image3 = new Image("Image from file", resource3);
        image3.setWidth("400px");
        image3.setHeight("300px");
        image3.setVisible(false);

        Image image4 = new Image("Image from file", resource4);
        image4.setWidth("400px");
        image4.setHeight("300px");
        image4.setVisible(true);

        image4.addClickListener(event->{
            Notification.show("Thanks for Clicking Image");
        });

        Button button = new Button("Click Me");
        button.addClickListener(event -> {
            layout.addComponent(new Label("Thank you for clicking"));
            button1.setEnabled(!button1.isEnabled());
        });
        layout.addComponent(button);
        layout.addComponent(button1);
        image_layout.addComponent(image1);
        image_layout.addComponent(image2);
        image_layout.addComponent(image3);
        image_layout.addComponent(image4);

        layout.addComponent(image_layout);



        new Thread(()-> {
            while (true) try {
                Thread.sleep(3000);
                access(() -> {
                    button1.setEnabled(!button1.isEnabled());
                    image1.setVisible(!image1.isVisible());
                    image2.setVisible(!image2.isVisible());
                    image3.setVisible(!image3.isVisible());
                    image4.setVisible(!image4.isVisible());

                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
