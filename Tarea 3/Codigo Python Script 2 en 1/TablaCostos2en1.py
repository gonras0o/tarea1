
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
		self.printTablaFinal()
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
		self.printTablaFinal()

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
				"""
				j=1
				for i in NodosActualizados:
					for n in self.Routers[i].Vecinos:
						if(self.Routers[n].TablaCostos[n][NodosActualizados[j]]==self.Routers[n].TablaCostos[n][i]+ValorAnterior):
							self.Routers[n].TablaCostos[n][j]=0
					j=0
					"""

	def printTablaFinal(self):
		for r in self.Routers:
			print "tabla "+r.Id,r.digitoPos
			for i in range(9):
				print r.TablaCostos[i]
			print "\n"
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

