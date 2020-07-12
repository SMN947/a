package com.smn947.servermonitor.db;
import java.util.*;

public class ping {

		private String id;
		private String nombre;
		private String tipo;
		private String destino;
		private String estado;

		public ping (String nombre, String tipo, String destino, String estado) {
				this.id = UUID.randomUUID().toString();
				this.nombre = nombre;
				this.tipo = tipo;
				this.destino = destino;
				this.estado = estado;
			}

		public String getId () {
				return id;
			}

		public String getNombre () {
				return nombre;
			}

		public String getTipo () {
				return tipo;
			}

		public String getDestino () {
				return destino;
			}

		public String getEstado() {
				return estado;
			}

	}
