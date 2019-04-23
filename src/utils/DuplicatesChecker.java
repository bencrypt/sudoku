package utils;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DuplicatesChecker<T> {

    public Set<T> getAllDuplicatesInList(List<T> toCheck) {

        Set<T> duplicates = new TreeSet<>();

        if (toCheck == null || toCheck.size() < 2) {
            return duplicates;
        }

        for (int check = 0; check < toCheck.size(); check++) {
            for (int compare = check+1; compare < toCheck.size(); compare++) {
                if (toCheck.get(check).equals(toCheck.get(compare))) {
                    duplicates.add(toCheck.get(check));
                }
            }
        }

        return duplicates;
    }
}
