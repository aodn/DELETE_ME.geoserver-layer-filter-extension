/*
 * Copyright 2014 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.geoserver.extensions.filters;

import au.org.emii.geoserver.extensions.filters.layer.data.Filter;
import au.org.emii.geoserver.extensions.filters.layer.data.FilterConfiguration;
import au.org.emii.geoserver.extensions.filters.layer.data.io.FilterConfigurationWriter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.geotools.util.logging.Logging;

import java.util.logging.Logger;

public class LayerFilterForm extends Form<FilterConfiguration> {

    // Because there is no clear way to set logging levels for your own packages
    static Logger LOGGER = Logging.getLogger("org.geoserver");

    public LayerFilterForm(final String id, IModel<FilterConfiguration> model) {
        super(id, model);

        add(new ListView<Filter>("layerDataProperties", model.getObject().getFilters())
        {
            public void populateItem(final ListItem<Filter> item)
            {
                final Filter filter = item.getModelObject();
                item.add(new CheckBox("propertyEnabled", new PropertyModel<Boolean>(filter, "enabled")));
                item.add(new Label("propertyName", filter.getName()));
                item.add(new Label("propertyType", filter.getType()));
                item.add(new TextField<String>("propertyLabel", new Model<String>() {
                    @Override
                    public String getObject() {
                        return filter.getLabel();
                    }

                    @Override
                    public void setObject(final String value) {
                        filter.setLabel(value);
                    }
                }));
                item.add(new CheckBox("propertyWmsFilter", new Model<Boolean>() {
                    @Override
                    public Boolean getObject() {
                        return new Boolean(!filter.getVisualised().booleanValue());
                    }

                    @Override
                    public void setObject(Boolean value) {
                        filter.setVisualised(new Boolean(!value.booleanValue()));
                    }
                }));
            }
        });

        add(saveLink());
        add(cancelLink());
    }

    private SubmitLink saveLink() {
        return new SubmitLink("save") {
            @Override
            public void onSubmit() {
                FilterConfiguration data = getModel().getObject();
                FilterConfigurationWriter writer = new FilterConfigurationWriter(data.getDataDirectory(), data.getFilters());
                writer.write();
            }
        };
    }

    private Link cancelLink() {
        return new Link("cancel") {

            @Override
            public void onClick() {

            }
        };
    }
}
