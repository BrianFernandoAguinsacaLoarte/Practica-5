
package controlador.TDA.listas;

import controlador.Excepcion.EmptyException;

import controlador.TDA.Nodo;

public class LinkedList<E>{
    private Nodo<E> head;
    private Nodo<E> last;
    private Integer size;
    
    public LinkedList() {
        head = null;
        last = null;
        size = 0;
    }
    
    public boolean isEmpty(){
        return head == null || getSize() == 0;
    }
    
    public void addFirst(E data){
        if(isEmpty()){
            Nodo<E> aux = new Nodo<>(data, null);
            head = aux;
            last = aux;
        }else{
            Nodo<E> headOld = head;
            Nodo<E> aux = new Nodo<>(data, headOld);
            head = aux;
        }
        size++;
    }
    
    public void addLast(E data){
        if (isEmpty()) {
            addFirst(data);
        }else{
            Nodo<E> aux = new Nodo<>(data, null);
            last.setNext(aux);
            last = aux;
            size++;
        }
        
    }
    
    public void add(E data){
        addLast(data);
    }
    
    public void add(E data, Integer post) throws EmptyException{
        if (post == 0) {
            addFirst(data);
        }else if(post.intValue() == (size.intValue())){
            addLast(data);
        }else{
            Nodo<E> search_preview = getNode(post - 1);
            Nodo<E> search = getNode(post);
            Nodo<E> aux = new Nodo<>(data, search);
            search_preview.setNext(aux);
            //setSize((Integer) (getSize() + 1));
            size++;
        }
    }
    
    public E getFirst() throws EmptyException{
        if(isEmpty()) {
            throw new EmptyException("Lista vacia");
        }else{
            return head.getData();
        }
    }
    
    public E getLast() throws EmptyException{
        if(isEmpty()) {
            throw new EmptyException("Lista vacia");
        }else{ 
            return last.getData();
        }
    }
    
    public E get(Integer index) throws EmptyException{
        if(isEmpty()) {
            throw new EmptyException("Lista vacia");
        }else if(index.intValue() < 0 || index.intValue() >= size){ 
            throw new IndexOutOfBoundsException("Fuera de rango");
        }else if(index.intValue() == 0){
            return getFirst();
        }else if(index.intValue() == size-1){
            return getLast();
        }else{
            Nodo<E> search = getNode(index);
            return search.getData();
        }
    }
    
    public Nodo<E> getNode(Integer post) throws EmptyException{
        if (isEmpty()) {
            throw new EmptyException("Error, La lista está vacia");
        }else if(post < 0 || post >= size){
            throw new IndexOutOfBoundsException("Error, Esta fuera de los límites de la lista");
        }else if(post == 0){
            return head;
        }else if(post == (size - 1 )){
            return last;
        }else {
            Nodo<E> search = head;
            Integer cont = 0;
            while(cont < post){
                cont++;
                search = search.getNext();
            }
            return search;
        }
        
    }
    
    public String print(){
        StringBuilder sb = new StringBuilder();
        if(isEmpty()){
            sb.append("Lista vacia");
        }else{
            Nodo<E> aux = head;
            while(aux != null){
                sb.append(aux.getData().toString()).append("\n"); 
                aux = aux.getNext();
            }
        }
        return sb.toString();
    }
    public void update(E data,Integer pos) throws EmptyException{
        if(isEmpty()){
            throw new EmptyException("Error, La lista esta vacia");
        }else if(pos < 0 || pos >= size){
            throw new IndexOutOfBoundsException("Error, Esta fuera de los limites de la lista");
        }else if(pos == 0){
            head.setData(data); //Si modifico la cabecera setData y le asigno data
        }else if(pos == (size -1)){
            last.setData(data);//Si modifico el last setData y le asigno data
            
        }else{
            Nodo<E> search = head;
            Integer cont = 0;
            while(cont < pos){
                cont++;
                search = search.getNext();
            }
            search.setData(data);
        }
    }
      
    public void clear(){
        head = null;
        last = null;
        size = 0;
    }
    
    public E[] toArray(){
        Class clazz = null;
        E[] matriz = null;
        if(this.size > 0){
            clazz = head.getData().getClass();
            matriz = (E[])java.lang.reflect.Array.newInstance(clazz, size);
            Nodo<E> aux = head;
            for(int i=0; i < size; i++){
                matriz[i] = aux.getData();
                aux = aux.getNext();
            }
        }
        return matriz;
    }
    
    public LinkedList<E> toList(E[] m){
        clear();
        for(int i=0; i < m.length; i++){
            this.add(m[i]);
        }
        return this;
    }
     
    //Getter and Setter

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    
    }
    
    public E deleteFirst() throws EmptyException {
        if(isEmpty()) {
            throw new EmptyException("Lista vacia");
        } else {
            E element = head.getData();
            Nodo<E> aux = head.getNext();
            head = aux;
            if(size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }
    
    public E deleteLast() throws EmptyException {
        if(isEmpty()) {
            throw new EmptyException("Lista vacia");
        } else {
            E element = last.getData();
            Nodo<E> aux = getNode(size - 2);
            if(aux == null) {
                last = null;
                if(size == 2) {
                    last = head;
                } else {
                    head = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }
    
    public E delete (Integer post) throws EmptyException {
        if(isEmpty()) {
            throw new EmptyException("Error, la lista esta vacia");
        } else if(post < 0 || post >= size) {
            throw  new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if(post == 0) {
            return deleteFirst();
        } else if(post == (size - 1)) {
            return deleteLast();
        } else {
            Nodo<E> preview = getNode(post - 1);
            Nodo<E> actually = getNode(post);
            E element = preview.getData();
            Nodo<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }
    }
    
    public static void main(String[] args) throws EmptyException {
        LinkedList<Integer> numerics = new LinkedList<>();
        for(int i = 0; i < 10; i++){
            Integer aux = (int)(Math.random()*1000);
            numerics.add(aux);
        }
        
        System.out.println(numerics.print());
        System.out.println("Tamaño de lista "+numerics.getSize());
        try {
            numerics.add(1,numerics.getSize());
            System.out.println("----------------------");
            System.out.println(numerics.print());
            //System.out.println(numerics.getNode(1).getData().toString());
            System.out.println("Tamaño de lista "+numerics.getSize());
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

  
}