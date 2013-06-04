package heap;

import excecoes.HeapUnderflowException;
import excecoes.KeyUpdateException;
import fila.MinPriorityQueue;

/**
* * Implementa uma fila de prioridade de minimo como heap.
*/

public class MinHeapPriorityQueue extends MinHeap implements MinPriorityQueue
{
	  /** Cria uma fila de prioridade de minimo vazia. */
   public MinHeapPriorityQueue()
   {
	super();
   }

   /**
   * Sobrepoe o metodo 'troca' para atualizar o indice de cada Handle.
   *
   * @param i indice de a.
   * @param j indice de b.
   */
   protected void troca(int i, int j)
   {
	((Handle) vetor[i]).indice = j;
	((Handle) vetor[j]).indice = i;
	super.troca(i,j);
   }
   
   /**
    * Sobe o elemento ate que ele seja maior ou igual ao seu pai, semelhantemente ao bubbleSort.
    *
    * @param i Indice do elemento.
    */
   private void bubbleUp(int i)
   {
	while (i > 0 && vetor[pai(i)].compareTo(vetor[i]) > 0) {
	    troca(i, pai(i));
	    i = pai(i);
	}
   }

   /**
   * Insere um DynamicSetElement na fila de prioridade.
    *
    * @param x Elemento a ser inserido
    * @return Um gerenciador para o item inserido. Esse gerenciador
    * eh como o item eh acessado numa operacao de diminuir a chave.
    */
   public Object insert(DynamicSetElement x)
   {
	// Se o vetor nao existe, cria.
	if (vetor == null) {
	    vetor = new Comparable[1];
	    heapTam = 0;
	}
	// Se nao tem espaco suficiente para o elemento, dobra o tamanho do vetor.
	else if (heapTam >= vetor.length) {
	    Comparable[] temp = new Comparable[heapTam * 2];

	    for (int i = 0; i < heapTam; i++)
		temp[i] = vetor[i];

	    vetor = temp;
	}

	// Cria um novo Handle e coloca no proximo indice disponivel.
	Handle handle = new Handle(heapTam, x);
	vetor[heapTam] = handle;
	heapTam++;

	// Chama o metodo Bubble Up para o elemento subir na heap.
	bubbleUp(heapTam-1);

	// Retorna a referencia para o handle.
	return handle;
   }

   /**
    * Retorna o menor elemento na fila sem remove-lo.
    *
    * @throws HeapUnderflowException se a fila estiver vazia.
    */
   public DynamicSetElement minimum() throws HeapUnderflowException
   {
	if (heapTam > 0)
	    return ((Handle) vetor[0]).info;
	else {
	    throw new HeapUnderflowException();
	}
   }
   
   /**
    * Remove e retorna o menor elemento na fila de prioridade.
    * 
    * @throws HeapUnderflowException Se a fila de prioridade estiver vazia.
    */
   public DynamicSetElement extractMin()
   {
	if (heapTam < 1)
	    throw new HeapUnderflowException();

	// Recebe a referencia do menor elemento.
	DynamicSetElement min = ((Handle) vetor[0]).info;

	// Move o ultimo elemento da heap para a raiz e limpa
	// a referencia na sua posicao atual do vetor
	vetor[0] = vetor[heapTam-1];
	((Handle) vetor[0]).indice = 0;
	vetor[heapTam-1] = null;
	heapTam--;

	// Restaura as propriedades da heap
	heapifica(0);

	return min;
   }
   
   /**
    * Diminui a chave de um dado elemento.
    *
    * @param element Handle que identifica o elemento. Esse Handle eh o dado como retorno do metodo insert.
    * @param newKey A nova chave para esse elemento.
    * @throws KeyUpdateException Se a nova chave eh maior que o valor atual.
    */
   public void decreaseKey(Object element, Comparable newKey)
	throws KeyUpdateException
   {
	Handle handle = (Handle) element;

	if (newKey.compareTo(handle.info.getKey()) > 0)
	    throw new KeyUpdateException();

	handle.info.setKey(newKey); // coloca a nova chave
	bubbleUp(handle.indice);	    // restaura a propriedade de heap.
   }

   
}