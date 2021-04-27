package it.polimi.ingsw.model.warehouse;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.exceptions.DiscardResourceException;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.ResourceTag;

import java.util.*;
import java.util.stream.Collectors;


public class Warehouse {
    private ArrayList<Shelf> shelves;


    public ArrayList<Shelf> getShelves() {
        return shelves;
    }


    /**
     * warehouse constructor, initializes shelves as an arraylist of size 3 with all elements as
     * UNDEFINED with 0 quantity, this means that the slot can be filled
     */
    public Warehouse (){
        shelves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            shelves.add(new Shelf(ResourceType.UNDEFINED, i+1));
        }
    }


    /**
     *
     * @param type -- given by the ExtraInventoryLeader card, states what type of resource the new
     *                shelf needs to be
     */
    public void addExtraInventory(ResourceType type){
        shelves.add(new Shelf(type, 2));
    }


    /**
     *
     * @return -- true if warehouse follows the rule of the game which are
     *            1) no two shelves can  hold the same type of resource (unless one of them comes from a leader card)
     *            2) each shelf must hold a quantity that is less or equal its maximum capacity
     */
    public boolean isWarehouseValid(){
        Map<ResourceType, Integer> validation = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            if(shelves.get(i).isValid()){
                Integer count = validation.get(shelves.get(i).getType());
                if(count == null){
                    validation.put(shelves.get(i).getType(), 1);
                }
                else{
                    validation.put(shelves.get(i).getType(), ++count);
                }
            }
            else return false;
        }
        for (int i = 2; i < shelves.size(); i++) {
            if(!shelves.get(i).isValid()) return false;
        }
        Set <Map.Entry<ResourceType, Integer>> entrySet = validation.entrySet();
        for (Map.Entry<ResourceType, Integer> iterator : entrySet) {
            if(iterator.getValue() > 1 && !iterator.getKey().equals(ResourceType.UNDEFINED)){
                return false;
            }
        }
        return true;
    }


    /**
     *
     * @param desired -- desired type of resource that whose presence in the shelves needs to be checked
     * @return -- states if at least one instance of that resource is found in the warehouse
     */
    public boolean checkPresence(ResourceType desired){
        Optional<Shelf> check =
                shelves.stream()
                .filter(shelf -> shelf.getElement().getType().equals(desired))
                .findFirst();
        return check.isPresent();
    }


    /**
     * sorts shelves based on the quantity they hold, this guarantees that the player will always
     * have the optimum arrangement of resources whenever they add a new one
     */
    public void sortShelves() {

        ArrayList<Shelf> clonedShelves = new ArrayList<>(shelves.size());
        for (Shelf iterator : shelves) {
            clonedShelves.add(new Shelf(iterator));
        }
        ArrayList<ResourceTag> tempSorted =
                (ArrayList<ResourceTag>)
                clonedShelves.stream()
                .limit(3)
                .sorted(Comparator.comparing(Shelf::getQuantity))
                .map(Shelf::getElement)
                .collect(Collectors.toList());
        for (int i = 0; i < 3; i++) {
            shelves.get(i).setElement(tempSorted.get(i));
        }
    }


    /**
     * if the quantity of a shelf is 0, it sets its type to UNDEFINED stating
     * that it can hold any resource
     */
    public void clearShelves(){
        for (int i = 0; i < 3; i++) {
            shelves.get(i).clear();
        }
    }


    /**
     *
     * @param currentShelf -- shelf in which we are trying to add a new resource
     * @return -- states if the resource was correctly placed or not
     * if the shelf is not extra then we need to check if any swap is available to let it hold another resource
     * if the shelf is extra then we need to check if there is a normal shelf of the same type
     * that can hold the resource or check for an empty slot to place the resource
     */
    public boolean tryAlternative(Shelf currentShelf){
        int index = shelves.indexOf(currentShelf);
        if(index < 2){
            if(currentShelf.getElement().getQuantity() <= shelves.get(shelves.indexOf(currentShelf)+1).getCapacity()
             && shelves.get(shelves.indexOf(currentShelf)+1).getQuantity() <= currentShelf.getCapacity()){
                ResourceTag temp = new ResourceTag(currentShelf.getElement().getType(), currentShelf.getQuantity());
                currentShelf.setElement(shelves.get(shelves.indexOf(currentShelf)+1).getElement());
                shelves.get(shelves.indexOf(currentShelf)+1).setElement(temp);
                return true;
            }
            else return false;
        }
        else if (index > 2){
            Optional <Shelf> temp = shelves.stream()
                                .limit(3)
                                .filter(shelf -> shelf.getElement().getType().equals(currentShelf.getElement().getType()))
                                .findFirst();
            if (temp.isPresent()){
                shelves.get(shelves.indexOf(temp.get())).increaseQuantity();
                if(shelves.get(shelves.indexOf(temp.get())).isValid()){
                    currentShelf.decreaseQuantity();
                    return true;
                }
                else{
                    shelves.get(shelves.indexOf(temp.get())).decreaseQuantity();
                    return false;
                }
            }
            else {
                temp = shelves.stream()
                        .filter(shelf -> shelf.getElement().getType().equals(ResourceType.UNDEFINED))
                        .findFirst();
                if(temp.isPresent()){
                    shelves.get(shelves.indexOf(temp.get())).getElement().setType(currentShelf.getType());
                    shelves.get(shelves.indexOf(temp.get())).increaseQuantity();
                    currentShelf.decreaseQuantity();
                    return true;
                }
            }
            return false;
        }
        else return false;
    }


    /**
     *
     * @param input -- resource that needs to be placed
     * @return -- states if the resource was correctly placed in an empty slot or not
     */
    public boolean placeInEmpty(MaterialResource input){

        Optional <Shelf> temp =
                shelves.stream()
                .filter(Shelf -> Shelf.getType().equals(ResourceType.UNDEFINED))
                .findFirst();
        if(temp.isPresent()){
            shelves.get(shelves.indexOf(temp.get())).getElement().setType(input.getResourceType());
            shelves.get(shelves.indexOf(temp.get())).increaseQuantity();
            return true;
        }
        else return false;
    }


    /**
     *
     * @param input -- resource that the player wants to place in their warehouse
     * @throws DiscardResourceException -- exception that states that the current state of the warehouse+
     *                                     cannot hold any more of the resource in input, thus signaling
     *                                     to the player that any further instance of said resource needs
     *                                     to be discarded
     */
    public void addResource (MaterialResource input) throws DiscardResourceException {
        if (checkPresence(input.getResourceType())){
            boolean placed = false;
            ArrayList<Shelf> temp =
                    (ArrayList<Shelf>) shelves.stream()
                    .filter(shelf -> shelf.getElement().getType().equals(input.getResourceType()))
                    .collect(Collectors.toList());
            Collections.reverse(temp);

            for (Shelf iterator :  temp) {
                if(!placed){
                    shelves.get(shelves.indexOf(iterator)).increaseQuantity();
                    if(shelves.get(shelves.indexOf(iterator)).isValid()){
                        placed = true;
                    }
                    else{
                        if(tryAlternative(shelves.get(shelves.indexOf(iterator)))){
                            placed = true;
                        }
                        else shelves.get(shelves.indexOf(iterator)).decreaseQuantity();
                    }
                }
            }
            sortShelves();
            if(!placed){
                throw new DiscardResourceException();
            }
        }
        else{
            if(placeInEmpty(input)) {
                sortShelves();
            }
            else {
                throw new DiscardResourceException();
            }
        }
    }


    /**
     *
     * @param tag - type and quantity of resource that wants to be removed from warehouse
     * @throws CannotRemoveResourceException -- exception that states that the request could not be
     *                                         executed, how much of the request is left
     *                                         (meaning that the request was either not executed at all
     *                                         or partially executed) signaling the player that the remaining
     *                                         quantity needs to be removed from somewhere else
     *                                         or that the request will never be fulfilled
     */
    public void removeResources(ResourceTag tag) throws CannotRemoveResourceException {
        if(checkPresence(tag.getType())){
            int quantity = tag.getQuantity();
            for (Shelf iterator : shelves){
                if(iterator.getElement().getType().equals(tag.getType())){
                    while (quantity > 0 && iterator.getQuantity()>0){
                        iterator.decreaseQuantity();
                        quantity--;
                    }
                }
            }
            clearShelves();
            sortShelves();
            if(quantity > 0) throw new CannotRemoveResourceException(tag.getType(), quantity);
        }
        else{
            throw  new CannotRemoveResourceException(tag.getType(), tag.getQuantity());
        }
    }
}
