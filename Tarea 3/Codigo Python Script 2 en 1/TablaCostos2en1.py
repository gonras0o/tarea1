from Tkinter import *

class PC:
	def __init__(self):
		self.Routers = [Router("A",VectoresDistancia=[-1,1,-1,-1,-1,-1,4,-1,10],Vecinos = [1,6,8],digitoPos=0),
		Router("B",VectoresDistancia=[1,-1,9,-1,8,-1,4,-1,-1],Vecinos = [0,2,4,6],digitoPos=1),
		Router("C",VectoresDistancia=[-1,9,-1,2,-1,-1,-1,-1,-1],Vecinos = [1,3],digitoPos=2),
		Router("D",VectoresDistancia=[-1,-1,2,-1,9,4,-1,-1,2],Vecinos = [2,4,5,8],digitoPos=3),
		Router("E",VectoresDistancia=[-1,8,-1,9,-1,2,-1,-1,1],Vecinos = [1,3,5,8],digitoPos=4),
		Router("F",VectoresDistancia=[-1,-1,-1,4,2,-1,-1,6,-1],Vecinos = [3,4,7],digitoPos=5),
		Router("G",VectoresDistancia=[4,-1,-1,-1,-1,-1,-1,7,-1],Vecinos = [0,7],digitoPos=6),
		Router("H",VectoresDistancia=[-1,-1,-1,-1,-1,6,7,-1,3],Vecinos = [5,6,8],digitoPos=7),
		Router("I",VectoresDistancia=[10,-1,-1,2,1,-1,-1,3,-1],Vecinos = [0,3,4,7],digitoPos=8)]
		NodosActualizados=[7,8]
		ValoresNuevo=0
		ValorAnterior=3

		for j in range(9):
			self.CalculoTablaCostosInicial()
			self.CalculoTablaCostosFinal()

		self.Aviso1()
		self.printTablaPrincipal()

		self.Routers[7].Vecinos.pop()
		self.Routers[8].Vecinos.pop()
		self.Routers[7].VectoresDistancia[8]=0
		self.Routers[8].VectoresDistancia[7]=0

		for z in range(9):
			self.Routers[z].RellenaTablaInicial()
		for i in range(9):
			self.CalculoTablaCostosInicial()
			self.CalculoTablaCostosFinal()

		self.AjusteMatrizDiagonal()
		self.Aviso2()
		self.printTablaNodoCortado()
		

	def PrintTablas(self):
		for n in self.Routers:
			print n.VectoresDistancia
			print "\n"

	def CalculoTablaCostosInicial(self):
		for r in self.Routers:
			for n in r.Vecinos:
				for i in range(9):
					for j in range(9):
						if(self.Routers[n].TablaCostos[i][j]>0 and i!=j):
							if(r.TablaCostos[i][j]>self.Routers[n].TablaCostos[i][j] or r.TablaCostos[i][j]==0):
								r.TablaCostos[i][j]=self.Routers[n].TablaCostos[i][j]

	def CalculoTablaCostosFinal(self):
		for r in self.Routers:
			i = r.digitoPos
			for j in range(9):
				for n in r.Vecinos:
					if(i!=j and r.TablaCostos[n][j]!=0):
						if(r.TablaCostos[i][j]==0):
							r.TablaCostos[i][j]=r.TablaCostos[n][j]+r.TablaCostos[i][n]
						elif(r.TablaCostos[i][j]>0):
							if(r.TablaCostos[i][j]>r.TablaCostos[n][j]+r.TablaCostos[i][n]):
								r.TablaCostos[i][j]=r.TablaCostos[n][j]+r.TablaCostos[i][n]

	def ActualizacionTabla(self,NodosActualizados,ValorAnterior,ValoresNuevo):
		for r in self.Routers:
				r.TablaCostos[7][8]=0
				r.TablaCostos[8][7]=0
				for n in r.Vecinos:
					if(n==NodosActualizados[0]):
						r.TablaCostos[r.digitoPos][8]=0
						r.TablaCostos[8][r.digitoPos]=0
					elif(n==NodosActualizados[1]):
						r.TablaCostos[r.digitoPos][7]=0
						r.TablaCostos[7][r.digitoPos]=0

	def printTablaPrincipal(self):
		labels = [None] * 9
		ventana = [None] * 9
		labelFrom = [None] * 9
		labelTo = [None] * 9
		labelFromLt = [None] * 9
		labelToLt = [None] * 9

		for n in range(9):
			labels[n] = [None] * 9

		for r in self.Routers:
			ventana[r.digitoPos] = Tk()
			ventana[r.digitoPos].title("Tabla de Costos router: "+r.Id)
			
			for i in range(9):
				labelFrom[i]=Label(ventana[r.digitoPos],text="From")
				labelFrom[i].grid(row=2,column=1)
				for z in range(9):
					labelFromLt[z]=Label(ventana[r.digitoPos],text=self.Routers[z].Id)
					labelFromLt[z].grid(row=z+3,column=1)
					labelToLt[z]=Label(ventana[r.digitoPos],text=self.Routers[z].Id)
					labelToLt[z].grid(row=1,column=z+3)

				labelTo[i]=Label(ventana[r.digitoPos],text="Cost To")
				labelTo[i].grid(row=1,column=2)
				for j in range(9):
					a="     "
					labels[i][j]=Label(ventana[r.digitoPos],text=str(r.TablaCostos[i][j])+a)
					labels[i][j].grid(row=i+3,column=j+3)

			finish = Button(ventana[r.digitoPos],text="Cerrar",relief=FLAT, command=lambda:self.destruir(ventana[r.digitoPos]))
			finish.grid(row=i+6,column=j/2+2)
			ventana[r.digitoPos].mainloop()

	def printTablaNodoCortado(self):
		labels = [None] * 9
		ventana = [None] * 9
		labelFrom = [None] * 9
		labelTo = [None] * 9
		labelFromLt = [None] * 9
		labelToLt = [None] * 9

		for n in range(9):
			labels[n] = [None] * 9

		for r in self.Routers:
			ventana[r.digitoPos] = Tk()
			ventana[r.digitoPos].title("Tabla de Costos router Luego del Corte H <-> I: "+r.Id)
			
			for i in range(9):
				labelFrom[i]=Label(ventana[r.digitoPos],text="From")
				labelFrom[i].grid(row=2,column=1)
				for z in range(9):
					labelFromLt[z]=Label(ventana[r.digitoPos],text=self.Routers[z].Id)
					labelFromLt[z].grid(row=z+3,column=1)
					labelToLt[z]=Label(ventana[r.digitoPos],text=self.Routers[z].Id)
					labelToLt[z].grid(row=1,column=z+3)

				labelTo[i]=Label(ventana[r.digitoPos],text="To")
				labelTo[i].grid(row=1,column=2)
				for j in range(9):
					a="     "
					labels[i][j]=Label(ventana[r.digitoPos],text=str(r.TablaCostos[i][j])+a)
					labels[i][j].grid(row=i+3,column=j+3)

			finish = Button(ventana[r.digitoPos],text="Cerrar",relief=FLAT, command=lambda:self.destruir(ventana[r.digitoPos]))
			finish.grid(row=i+6,column=j/2+2)
			ventana[r.digitoPos].mainloop()

	def Aviso1(self):
		ventana = Tk()
		ventana.title("AVISOOOOOOO 1")
		label=Label(ventana,text="Aviso: las ventanas a continuacion contienen las tablas de costo de todos los Routers")
		label.grid(row=1,column=1)
		finish = Button(ventana,text="Cerrar",relief=FLAT, command=lambda:self.destruir(ventana))
		finish.grid(row=2,column=5)
		ventana.mainloop()

	def Aviso2(self):
		ventana = Tk()
		ventana.title("AVISOOOOOOO 2")
		label1=Label(ventana,text="Aviso 2: Las siguientes ventanas contienen las tablas de costo")
		label1.grid(row=1,column=1)
		label2=Label(ventana,text="         despues de que un trasatlantico cortara la conexion entre el nodo H <-> I")
		label2.grid(row=2,column=1)
		finish = Button(ventana,text="Cerrar",relief=FLAT, command=lambda:self.destruir(ventana))
		finish.grid(row=3,column=5)
		ventana.mainloop()

	def destruir(self,ventana):
		ventana.destroy()

	def AjusteMatrizDiagonal(self):
		for r in self.Routers:
			for i in range(9):
				for j in range(9):
					r.TablaCostos[j][i]=r.TablaCostos[i][j]

class Router:
	def __init__(self,IdRouter,VectoresDistancia,Vecinos,digitoPos):
		self.Vecinos = Vecinos
		self.digitoPos=digitoPos
		self.Id = IdRouter
		self.VectoresDistancia = VectoresDistancia
		self.TablaCostos = [None] * 9
		for i in range(9):
			self.TablaCostos[i] = [None] * 9
		self.RellenaTablaInicial()

	def RellenaTablaInicial(self):
		for i in range(9):
			for j in range(9):
				self.TablaCostos[i][j]=0

		for n in range(9):
			if(self.VectoresDistancia[n]!=(-1)):
				self.TablaCostos[self.digitoPos][n]=self.VectoresDistancia[n]

	def PrintTablaInicial(self):
		print "tabla "+self.Id
		for i in range(9):
			print self.TablaCostos[i]
		print "\n"
MiPC = PC()

