# 前端
前端提供的方案不一定优雅但是够用了。
## 数据字典
在登录后`/src/api/sys/index.ts` initAllDict 加载所有字典放入sessionStorage中，业务模块字典可用用于form表单，
或者页面回显数据时候使用，业务模块字典约定在data.ts中。

### 手动编写字典
```typescript
export const templateTypes: Dict[] = [
  {
    label: '基础增删改查',
    value: 0,
  },
  {
    label: '树结构',
    value: 1,
    disabled: true,
  },
];

export const templateTypesMap = dictArray2Map(templateTypes);

```
### 加载DB字典
```typescript
export const xxxDicts: Dict[] = getDict('xxxx')

export const xxxDictsMap = dictArray2Map(templateTypes);
```
> 默认字典的value都是字符串类型，如果需要转整形，使用getDict('xxxx',true)，表单校验对数类型很敏感。

### 字典使用
- 渲染select,radio,checkbox等。
```html
<a-select
      v-model:value="dataForm.xxx"
      :allowClear="true"
      :options="xxxDicts"
      placeholder="请选择下拉数据"
      style="width: 140px"
    />
```
- 数据转换
```typescript
xxxDictsMap.get('value')
```

