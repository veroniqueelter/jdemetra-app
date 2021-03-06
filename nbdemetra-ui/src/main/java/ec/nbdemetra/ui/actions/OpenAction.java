/*
 * Copyright 2013 National Bank of Belgium
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved 
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
package ec.nbdemetra.ui.actions;

import org.netbeans.api.actions.Openable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Philippe Charles
 */
@ActionID(category = "File", id = "ec.nbdemetra.ui.actions.OpenAction")
@ActionRegistration(displayName = "#OpenAction", lazy = false)
@Messages("OpenAction=Open")
public final class OpenAction extends AbilityAction<Openable> {

    public OpenAction() {
        super(Openable.class);
    }

    @Override
    protected void performAction(Iterable<Openable> items) {
        for (Openable o : items) {
            o.open();
        }
    }

    @Override
    public String getName() {
        return Bundle.OpenAction();
    }
}
