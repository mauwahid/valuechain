package pdf.mandiri.action;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;

import pdf.mandiri.domain.Pengguna;

public class MenuAction extends GenericForwardComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8796907865310171981L;
	
	Label username;
	Pengguna pengguna;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		pengguna = (Pengguna) session.getAttribute("pengguna");
		username.setValue(pengguna.getNama());
	}

	
}
