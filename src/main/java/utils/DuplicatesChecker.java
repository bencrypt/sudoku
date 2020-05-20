package utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DuplicatesChecker<T> {

    /**
     * Returns all duplicate values of the Type <code>T</code> in the given list.
     *
     * @param toCheck
     *      the list to check for duplicates
     * @return
     *      a set with each duplicate value
     */
    public Set<T> getAllDuplicatesInList(List<T> toCheck) {

        // create the result set
        Set<T> duplicates = new HashSet<>();

        // if the list is null or has only one element, there cannot be a duplicate
        if (toCheck == null || toCheck.size() < 2) {
            return duplicates;
        }

        // compare each element with the following elements
        // --> if equal, add to duplicate list
        for (int check = 0; check < toCheck.size(); check++) {
            for (int compare = check+1; compare < toCheck.size(); compare++) {
                if (toCheck.get(check).equals(toCheck.get(compare))) {
                    duplicates.add(toCheck.get(check));
                }
            }
        }

        // return the filled set
        return duplicates;
    }
}
