import { menuTree } from '../../api/sys';

export const menuTreeMixin = {
  mounted() {
    this.loadMenuData();
  },
  data() {
    return {
      menuTreeData: [],
    };
  },
  methods: {
    loadMenuData(treeNode) {
      return new Promise((resolve) => {
        if (treeNode && treeNode.dataRef.children) {
          resolve();
          return;
        }
        menuTree(treeNode ? treeNode.dataRef.value : 0, true).then(({ data }) => {
          if (!treeNode) {
            this.menuTreeData = data;
          } else {
            treeNode.dataRef.children = data;
          }
          resolve();
        });
      });
    },
  },
};
