/*
 * Copyright 2016 National Bank of Belgium
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package ec.nbdemetra.tramoseats.actions;

import ec.nbdemetra.tramoseats.TramoSpecificationManager;
import ec.nbdemetra.ui.Config;
import ec.nbdemetra.ui.interchange.ImportAction;
import ec.nbdemetra.ui.interchange.Importable;
import ec.nbdemetra.ui.nodes.SingleNodeAction;
import ec.nbdemetra.ws.WorkspaceFactory;
import ec.nbdemetra.ws.WorkspaceItem;
import ec.tss.tsproviders.utils.Parsers;
import ec.tss.xml.information.XmlInformationSet;
import ec.tstoolkit.algorithm.IProcSpecification;
import ec.tstoolkit.modelling.arima.tramo.TramoSpecification;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

/**
 * Action on Tramo specification workspace node allowing the import
 *
 * @author Mats Maggi
 */
@ActionID(category = "Edit", id = "ec.nbdemetra.tramoseats.actions.ImportTramoSpec")
@ActionRegistration(displayName = "#CTL_ImportTramoSpec", lazy = false)
@ActionReferences({
    @ActionReference(path = TramoSpecificationManager.PATH, position = 1000)
})
@Messages("CTL_ImportTramoSpec=Import from")
public class ImportTramoSpec extends SingleNodeAction<Node> implements Presenter.Popup {

    public ImportTramoSpec() {
        super(Node.class);
    }

    @Override
    protected void performAction(Node activatedNode) {

    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenuItem result = ImportAction.getPopupPresenter(getImportables());
        result.setText(Bundle.CTL_ImportTramoSpec());
        return result;
    }

    @Override
    protected boolean enable(Node activatedNode) {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    private List<Importable> getImportables() {
        return Collections.<Importable>singletonList(new Importable() {

            @Override
            public String getDomain() {
                return TramoSpecification.class.getName();
            }

            @Override
            public void importConfig(Config config) throws IllegalArgumentException {
                TramoSpecification spec = fromConfig(config);
                if (spec != null) {
                    WorkspaceItem<IProcSpecification> ndoc = WorkspaceItem.newItem(TramoSpecificationManager.ID, config.getName(), spec);
                    WorkspaceFactory.getInstance().getActiveWorkspace().add(ndoc);
                }
            }
        });
    }

    private static TramoSpecification fromConfig(@Nonnull Config config) throws IllegalArgumentException {
        if (!TramoSpecification.class.getName().equals(config.getDomain())) {
            throw new IllegalArgumentException("Invalid config");
        }

        return config.getParam("specification")
                .map(Parsers.onJAXB(XmlInformationSet.class)::parse)
                .map(XmlInformationSet::create)
                .map(o -> {
                    TramoSpecification spec = new TramoSpecification();
                    spec.read(o);
                    return spec;
                })
                .orElse(null);
    }
}
