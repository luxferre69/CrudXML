package model;

public class Libro {

	private int id;
	private String categoria;
	private String titulo;
	private String autor;
	private int anio;
	private double precio;

	public Libro() {
		// TODO Auto-generated constructor stub
	}

	public Libro(int id, String categoria, String titulo, String autor, int anio, double precio) {
		
		this.id = id;
		this.categoria = categoria;
		this.titulo = titulo;
		this.autor = autor;
		this.anio = anio;
		this.precio = precio;

	}

	public int getId() {
		return id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
