import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static fb.faiss.faiss.*;

public class Main {
    public static void main(String[] args) {
        int k = 2;
        MemorySegment queries;
        int queriesCount;
        MemorySegment distances;
        MemorySegment labels;

        try (var memorySession = Arena.openShared()) {
            int dimensionality = 4;
            MemorySegment indexFactorString = memorySession.allocateUtf8String("Flat");
            // FaisIndex**
            MemorySegment faissIndexPtrPtr = memorySession.allocate(C_POINTER);

            // addr of FaisIndex**
            long faisIndexPtrPtrAddress = faissIndexPtrPtr.address();
            MemorySegment faisIndexPtrPtrSegment = MemorySegment.ofAddress(faisIndexPtrPtrAddress, C_POINTER.byteSize());

            faiss_index_factory(faisIndexPtrPtrSegment, dimensionality, indexFactorString, METRIC_L2());

            //dereference FaisIndex** to get FaisIndex*
            MemorySegment faissIndex = faisIndexPtrPtrSegment.get(C_POINTER, 0);

            MemorySegment vectors = memorySession.allocateArray(C_FLOAT, 1.0f, 1.1f, 1.2f, 1.56f, 2.78f, 2.322f, 2.3f, 2.4f);
            System.out.println("index size: " + faiss_Index_ntotal(faissIndex));

            faiss_Index_add(faissIndex, 2L, vectors);

            System.out.println("added vectors: " + faiss_Index_ntotal(faissIndex));

            queries = memorySession.allocateArray(C_FLOAT, 1.0f, 1.1f, 1.2f, 1.3f);
            queriesCount = 1;

            distances = memorySession.allocateArray(ValueLayout.JAVA_FLOAT, queriesCount * k);
            labels = memorySession.allocateArray(faiss_idx_t, queriesCount * k);
            faiss_Index_search(faissIndex, queriesCount, queries, k, distances, labels);

            for (int i = 0; i < k; i++) {
                System.out.println("distance: " + distances.get(C_FLOAT, i * C_FLOAT.byteSize()));
            }

            for (int i = 0; i < k; i++) {
                System.out.println("labels: " + labels.get(faiss_idx_t, i * faiss_idx_t.byteSize()));
            }
        }
    }

}