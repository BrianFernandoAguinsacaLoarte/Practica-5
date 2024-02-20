/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.arboles;

import controlador.TDA.listas.LinkedList;
import java.util.Arrays;

/**
 *
 * @author Usuario iTC
 */
public class TreeNumber {
    
    private NodeTree<Integer> root;
    private Integer height;
    private LinkedList<LinkedList<NodeTree>> levels;
    private Integer nro_nodes;
    private LinkedList<NodeTree> orders;

    public TreeNumber() {
        root = null;
        height=0;
        nro_nodes=0;
        levels= new LinkedList<>();
        orders = new LinkedList<>();
    }

    public NodeTree<Integer> getRoot() {
        return root;
    }

    public void setRoot(NodeTree<Integer> root) {
        this.root = root;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LinkedList<LinkedList<NodeTree>> getLevels() {
        return levels;
    }

    public void setLevels(LinkedList<LinkedList<NodeTree>> levels) {
        this.levels = levels;
    }

    public Integer getNro_nodes() {
        return nro_nodes;
    }

    public void setNro_nodes(Integer nro_nodes) {
        this.nro_nodes = nro_nodes;
    }

    public LinkedList<NodeTree> getOrders() {
        return orders;
    }

    public void setOrders(LinkedList<NodeTree> orders) {
        this.orders = orders;
    }
    
    
    
    private Integer calcHeight(NodeTree tree) throws Exception{
        if (tree == null) {
            return 0;
        } else {
            Integer left = calcHeight(tree.getLeft());
            Integer right = calcHeight(tree.getRight());
            if(left.intValue()> right.intValue())
                return left+1;
            else
                return right+1;
        }
    }
    
    private void calcLevels(NodeTree tree, Integer level) throws Exception {
        if (tree != null) {
            levels.get(level).add(tree);
            level++;
            calcLevels(tree.getLeft(), level);
            calcLevels(tree.getRight(), level);
        } else if (level.intValue() != getHeight().intValue()){
            levels.get(level).add(null);
            level++;
            calcLevels(null, level);
            calcLevels(null, level);
        }
        
    }
    
    
    private void levels() throws Exception {
        levels = new LinkedList<>();
        this.height = calcHeight(root);
        for (int i = 0; i <= this.height; i++) {
            levels.add(new LinkedList<>());
        }
        calcLevels(root, 0);
        levels.delete(levels.getSize() - 1);
    }

    private Boolean insert(Integer value) throws Exception {
        if (root == null) {
            root = new NodeTree<>(value);
            nro_nodes++;
            levels();
            return true;
        } else {
            NodeTree<Integer> fresh = new NodeTree<>(value);
            NodeTree<Integer> recent = root;
            NodeTree<Integer> father;
            while (true) {
                father = recent;
                if (value.intValue() == recent.getInfo().intValue()) {
                    return false;
                } else if (value.intValue() < recent.getInfo().intValue()) {
                    recent = recent.getLeft();
                    if (recent == null) {
                        fresh.setFather(father);
                        father.setLeft(fresh);
                        nro_nodes++;
                        levels();
                        return true;
                    }
                } else {
                    recent = recent.getRight();
                    if (recent == null) {
                        fresh.setFather(father);
                        father.setRight(fresh);
                        nro_nodes++;
                        levels();
                        return true;
                    }
                }
            }
        }
    }
    
    private void pre_order (NodeTree tree){
        if (tree != null) {
            orders.add(tree);
            pre_order(tree.getLeft());
            pre_order(tree.getRight());
            
        }
    }
    
    private void post_order (NodeTree tree){
        if (tree != null) {
            post_order(tree.getLeft());
            post_order(tree.getRight());
            orders.add(tree);
            
        }
    }
    private void in_order (NodeTree tree){
        if (tree != null) {
            in_order(tree.getLeft());
            orders.add(tree);
            in_order(tree.getRight());
            
            
        }
    }
    
    private LinkedList<NodeTree> routes(Integer type) {
        //1 pre order
        // 2 post order
        //any in order
        orders = new LinkedList<>();
        if (type == 1) {
            pre_order(root);
        } else if(type == 2) {
            post_order(root);
        } else
            in_order(root);
        return orders;
    }

    
    public static void main(String[] args) {
        
        try {
            TreeNumber tree = new TreeNumber();
            tree.insert(90);
            tree.insert(50);
            tree.insert(100);
            tree.insert(5);
            tree.insert(60);
            tree.insert(95);
            tree.insert(110);
            System.out.println("Altura "+ tree.getHeight());
            System.out.println("Levels " + tree.getLevels().getSize());
            
            System.out.println("Recorrido en pre orden");
            Arrays.stream(tree.routes(1).toArray()).forEach(data -> System.out.print(data+"--"));
            System.out.println("");
            
            System.out.println("Recorrido en ost orden");
            Arrays.stream(tree.routes(2).toArray()).forEach(data -> System.out.print(data+"--"));
            System.out.println("");
            
            System.out.println("Recorrido en in orden");
            Arrays.stream(tree.routes(0).toArray()).forEach(data -> System.out.print(data+"--"));
            System.out.println("");
            
            //LinkedList<NodeTree> route = (LinkedList<NodeTree>) tree.routes(1);
            //System.out.println(route.getSize());
            LinkedList<LinkedList<NodeTree>> levels = tree.getLevels();
            
            int maxHeight = levels.getSize();

            for (int i = 0; i < levels.getSize(); i++) {
                LinkedList<NodeTree> currentLevel = levels.get(i);
                int levelSize = currentLevel.getSize();

                // Calcular el espaciado para centrar verticalmente
                int spacesBefore = (int) Math.pow(2, maxHeight - i) - 1;
                int spacesBetween = (int) Math.pow(2, maxHeight - i + 1) - 1;

                // Imprimir espacios antes de cada nodo
                for (int j = 0; j < spacesBefore; j++) {
                    System.out.print(" ");
                }

                // Imprimir nodos con espacios entre ellos
                for (NodeTree node : currentLevel.toArray()) {
                    if (node != null) {
                        System.out.print(node.getInfo());

                        // Imprimir espacios entre nodos
                        for (int k = 0; k < spacesBetween; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        // Imprimir espacios para los nodos nulos
                        for (int k = 0; k < spacesBetween; k++) {
                            System.out.print(" ");
                        }
                    }
                }

                System.out.println(); // Cambiar de línea para el próximo nivel
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}
    
    
    
   
