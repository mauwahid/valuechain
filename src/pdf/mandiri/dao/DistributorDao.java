package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.domain.Kanwil;

public interface DistributorDao extends Dao<Distributor> {

	public List<Distributor> getByJoinDF();
	public List<Distributor> getAllAsc();
	public List<Distributor> getAllByKanwil(Kanwil kanwil);
	public List<Distributor> getAllByKanwilSearch(Kanwil kanwil,String nama);
	public List<String> getAllStringKanwil(Kanwil kanwil);


}
