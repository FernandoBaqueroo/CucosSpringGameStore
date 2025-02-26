package com.bolsadeideas.springboot.app.models.entity;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
@Table(name="facturas")
public class Factura implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	public Factura() {
		this.items= new ArrayList<ItemFactura>();
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	private String descripcion;
	private String observacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_at")
	private Date createAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Cliente cliente;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="factura_id")
	private List<ItemFactura> items;
	
	public List<ItemFactura> getItems() {
		return items;
	}
	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	@PrePersist
	public void prePersist()
	{
		createAt= new Date();
	}
	
	public void addItemFactura(ItemFactura item) {
		this.items.add(item);
	}
	public Double getTotal() {
		Double total=0.0;
		int size=items.size();
		for(int i=0;i<size;i++) {
			total=total+items.get(i).calcularImporte();
		}
		
		return total;
	}
	
	
	
	
	
	
	
	
	
	
	
}
