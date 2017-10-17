package pdf.mandiri.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import pdf.mandiri.dao.AnchorDao;
import pdf.mandiri.dao.AreaDao;
import pdf.mandiri.dao.BUCAnchorDao;
import pdf.mandiri.dao.CustomerTypeDao;
import pdf.mandiri.dao.DistributionFinanceDao;
import pdf.mandiri.dao.DistributorDao;
import pdf.mandiri.dao.KanwilDao;
import pdf.mandiri.dao.KreditTypeDao;
import pdf.mandiri.dao.StatusDao;
import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.CustomerType;
import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.KreditType;
import pdf.mandiri.domain.Status;
import pdf.mandiri.util.HibernateUtil;

public class UploadDistributorAction extends GenericForwardComposer<Component>  {

	private static final long serialVersionUID = 4312107444244847784L;
	Button btn_upload;
	Button btn_ekstrak;
	Label file_name;
	String inputFile;
	Media media;
	Workbook wb;
	File file;
	Sheet sheet;
	Cell cell;
	
	int defaultRow = 7;
	int defaultCol = 0;
	
	String nama;
	String kota;
	String noTelp;
	String namaPIC;
	String tanggal;
	String npwp;
	String area;
	String alamat;
	String outlet;
	String buc;
	String kanwil;
	
	Distributor distributor;
	
	DistributionFinanceDao dfDao;
	DistributionFinance distributionFinance;
	UploadEvent event;
	
	String path = "ekstrak\\";
	boolean hasil;
	
	
	KanwilDao kanwilDao = HibernateUtil.getKanwilDao();
	AnchorDao anchorDao = HibernateUtil.getAnchorDao();
	DistributorDao distributorDao = HibernateUtil.getDistributorDao();
	BUCAnchorDao bucDao = HibernateUtil.getBucAnchorDao();
	AreaDao areaDao = HibernateUtil.getAreaDao();
	StatusDao statusDao = HibernateUtil.getStatusDao();
	KreditTypeDao fasilitasDao = HibernateUtil.getKreditTypeDao();
	CustomerTypeDao cusTypeDao = HibernateUtil.getCustomerTypeDao();
	
	Kanwil kanwilObj;
	Anchor anchorObj;
	Distributor distributorObj;
	BUCAnchor bucObj;
	Area areaObj;
	Status statusObj;
	KreditType fasilitasObj;
	CustomerType custObj;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		

		
		path = session.getWebApp().getServletContext().getRealPath("/");
		path = path +"WebContent"+"\\"+"ekstrak\\";
		System.out.println("path : "+path);
		
		
	}
	
	
	public void onUploadData(UploadEvent event){
		media = event.getMedia();
		final String mediaName = media.getName();
		
		if(media!=null){
			
			String name= media.getName();
			
			File file = new File(path+mediaName);
			try {
				Files.copy(file, media.getStreamData());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("gagal");
			}
			
			Messagebox.show("Simpan data "+media.getName(), "Sukses", Messagebox.OK, null, new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO AutoloadData();
				//	onSave(BTN_AWAL, mediaName);
				}
			});
			
			file_name.setValue(mediaName);
			
		}
		else{
			Messagebox.show("Gagal", "Gagal", Messagebox.OK, null, new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO AutoloadData();
				}
			});
		}
		
	}
	
	public void onExtract(){
		inputFile = path+media.getName();
		file = new File(inputFile);
		try {
			wb = Workbook.getWorkbook(file);
			sheet = wb.getSheet(0);
			int j = 0;
			for(j = defaultRow; j<sheet.getRows();j++){
					nama = sheet.getCell(1,j).getContents();
					kota = sheet.getCell(2,j).getContents();
					noTelp = sheet.getCell(3,j).getContents();
					namaPIC = sheet.getCell(4,j).getContents();
					tanggal = sheet.getCell(5,j).getContents();
					npwp = sheet.getCell(6,j).getContents();
					area = sheet.getCell(7,j).getContents();
					alamat = sheet.getCell(8,j).getContents();
				//	System.out.println("customer type "+customerType);
					outlet = sheet.getCell(9,j).getContents();
					buc = sheet.getCell(10,j).getContents();
					kanwil = sheet.getCell(11,j).getContents();
					
		//			hasil = queryInsert(kanwil,anchor,distributor,buc,cabang,status,fasilitas,customerType,outstanding,limit,accNumber,cifNumber,outlet,potensi,keterangan,tanggal,j);
					hasil = queryInsert(nama,kota,noTelp,namaPIC,tanggal,npwp,area,alamat,outlet,buc,kanwil);
					
			
			}
			
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(hasil == true){
			Messagebox.show("Berhasil Upload Ke Database", "INFO", Messagebox.OK, Messagebox.INFORMATION, new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					
				}
			});

		}
		
		
		
	}
	
	private boolean queryInsert(String nama2, String kota2, String noTelp2,
			String namaPIC2, String tanggal2, String npwp2, String area2,
			String alamat2, String outlet2, String buc2, String kanwil2) {
		// TODO Auto-generated method stub
	
		
		distributorDao = HibernateUtil.getDistributorDao();
		distributor = new Distributor();
		
		bucDao = HibernateUtil.getBucAnchorDao();
		kanwilDao = HibernateUtil.getKanwilDao();
		areaDao = HibernateUtil.getAreaDao();
		
		bucObj = bucDao.getByName(buc2);
		kanwilObj = kanwilDao.getByNoKanwil(kanwil2);
		areaObj = areaDao.getByName(area2);
		
		distributor.setAlamat(alamat2);
		distributor.setNama(nama2);
		distributor.setKota(kota2);
		distributor.setNoTelp(noTelp2);
		distributor.setNamaPIC(namaPIC2);
		distributor.setNpwp(npwp2);
		distributor.setOutlet(outlet2);
		distributor.setBuc(bucObj);
		distributor.setArea(areaObj);
		distributor.setKanwil(kanwilObj);
		
		
		
		try{
			distributor.setTanggalBerdiri(new Date(tanggal2));
				
		}catch(IllegalArgumentException ex){
			
		}
		
		try{
			distributorDao.insert(distributor);
			
		}catch(Exception ex){
			Messagebox.show("EXCEL GAGAL DIEKSTRAK", "WARNING", Messagebox.OK, Messagebox.ERROR, new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					
				}
			});
			return false;
		}
		return true;
	}


	private boolean queryInsert(String kanwil2, String anchor2, String distributor2,String buc2,
			String cabang2, String status2, String fasilitas2,
			String customerType2, String outstanding2, String limit2,
			String accNumber2,String cifNumber2, String outlet2, String potensi2,
			String keterangan2, String tanggal2, int baris) {
		// TODO Auto-generated method stub
		baris = baris - 6;
		distributionFinance = new DistributionFinance();
		dfDao = HibernateUtil.getDistributionFinanceDao();
		
		kanwilObj = kanwilDao.getByNoKanwil(kanwil2);
		anchorObj = anchorDao.getByName(anchor2);
		distributorObj = distributorDao.getByName(distributor2);
		bucObj = bucDao.getByName(buc2);
		areaObj = areaDao.getByName(cabang2);
		statusObj = statusDao.getByName(status2);
		System.out.println("Customer type 2 "+customerType2);
		
		
		custObj = cusTypeDao.getByName(customerType2);
		
		distributionFinance.setKanwil(kanwilObj);
		distributionFinance.setAnchor(anchorObj);
		distributionFinance.setDistributor(distributorObj);
		distributionFinance.setBuc(bucObj);
		distributionFinance.setArea(areaObj);
		distributionFinance.setCurrentStatus(statusObj);
		distributionFinance.setFasilitasString(fasilitas2);
		distributionFinance.setCustomerType(custObj);
		distributionFinance.setCifNumber(cifNumber2);
		
		distributionFinance.setAccNumber(accNumber2);
		distributionFinance.setKeterangan(keterangan2);
		try{
			distributionFinance.setLimitDF(Double.parseDouble(limit2));
					
		}catch(NumberFormatException ex){
			
		}
		try{
			distributionFinance.setOutstandingList(Double.parseDouble(outstanding2));
			
		}catch(NumberFormatException ex){
			
		}
		distributionFinance.setOutlet(outlet2);
		
		try{
			distributionFinance.setPotensi(Double.parseDouble(potensi2));
			
		}catch(NumberFormatException ex){
			
		}
		
		try{
			distributionFinance.setTanggal(new Date(tanggal));
				
		}catch(IllegalArgumentException ex){
			
		}
		
		try{
			dfDao.insert(distributionFinance);
			
		}catch(Exception ex){
			Messagebox.show("EXCEL GAGAL DIEKSTRAK", "WARNING", Messagebox.OK, Messagebox.ERROR, new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					
				}
			});
			return false;
		}
		return true;				
	}
	
	public void onDownloadTemplate(){
		File myFile = new File(application
                .getRealPath("/laporan/vc_distributor_template.xls"));
		   
        try {
				Filedownload.save(myFile, "xls");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    
	}
}
