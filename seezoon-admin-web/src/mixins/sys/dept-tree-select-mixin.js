import { deptTree } from '../../api/sys';

export const deptTreeSelectMixin = {
  mounted() {
    this.loadDeptData();
  },
  data() {
    return {
      deptTreeData: [],
    };
  },
  methods: {
    loadDeptData(treeNode) {
      return new Promise((resolve) => {
        if (treeNode && treeNode.dataRef.children) {
          resolve();
          return;
        }
        deptTree(treeNode ? treeNode.dataRef.value : 0, true).then((data) => {
          if (!treeNode) {
            this.deptTreeData = data;
          } else {
            treeNode.dataRef.children = data;
          }
          resolve();
        });
      });
    },
  },
};
