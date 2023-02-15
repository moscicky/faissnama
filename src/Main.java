
import java.lang.foreign.*;

import static test.faiss.index_factory_c_h.*;

public class Main {
    public static void main(String[] args) {
        int k = 2;
        Addressable faissIndex;
        MemorySegment queries;
        int queriesCount;
        MemorySegment distances;
        MemorySegment labels;
        try (MemorySession memorySession = MemorySession.openImplicit()) {
            int dimensionality = 4;
            MemorySegment indexFactorString = memorySession.allocateUtf8String("Flat");
            faissIndex = MemoryAddress.NULL;
            faiss_index_factory(faissIndex, dimensionality, indexFactorString, METRIC_L2());

            MemorySegment vectors = memorySession.allocateArray(ValueLayout.JAVA_FLOAT, 1.0f, 1.1f, 1.2f, 1.3f, 2.1f, 2.2f, 2.3f, 2.4f);

            System.out.println("index size: " + faiss_Index_ntotal(faissIndex));

            faiss_Index_add(faissIndex, 4L, vectors);

            queries = memorySession.allocateArray(ValueLayout.JAVA_FLOAT, 1.0f, 1.1f, 1.2f, 1.3f);
            queriesCount = 1;

            distances = memorySession.allocateArray(ValueLayout.JAVA_FLOAT, queriesCount * k);
            labels = memorySession.allocate(faiss_idx_t, queriesCount * k);
            faiss_Index_search(faissIndex, queriesCount, queries, k, distances, labels);
        }

    }

}