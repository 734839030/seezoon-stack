import { isPC } from "./isPC"
export default {
	data() {
		return {
			position: [],
			button: {},
			btn: "[]"
		}
	},
	watch: {
		button: {
			handler(newVal) {
				this.btn = JSON.stringify(newVal)
			},
			deep: true
		},
		show(newVal) {
			if (this.autoClose) return
			if (!this.button) {
				this.init()
				return
			}
			this.button.show = newVal
		},
		leftOptions() {
			this.init()
		},
		rightOptions() {
			this.init()
		}
	},
	created() {
		this.swipeaction = this.getSwipeAction()
		if (this.swipeaction.children !== undefined) {
			this.swipeaction.children.push(this)
		}
	},
	mounted() {
		this.init()
	},
	// fixme by mehaotian 在页面激活的时候需要重新获取元素信息
	activated(){
		this.init()
	},
	methods: {
		init() {
			clearTimeout(this.swipetimer)
			this.swipetimer = setTimeout(() => {
				this.getButtonSize()
			}, 50)
		},
		closeSwipe(e) {
			if (!this.autoClose) return
			this.swipeaction.closeOther(this)
		},

		change(e) {
			this.$emit('change', e.open)
			let show = this.button.show
			if (show !== e.open) {
				this.button.show = e.open
			}

		},

		appTouchStart(e) {
			// #ifdef H5
			if(isPC()) return
			// #endif
			const {
				clientX
			} = e.changedTouches[0]
			this.clientX = clientX
			this.timestamp = new Date().getTime()
		},
		appTouchEnd(e, index, item, position) {
			// #ifdef H5
			if(isPC()) return
			// #endif
			const {
				clientX
			} = e.changedTouches[0]
			// fixed by xxxx 模拟点击事件，解决 ios 13 点击区域错位的问题
			let diff = Math.abs(this.clientX - clientX)
			let time = (new Date().getTime()) - this.timestamp
			if (diff < 40 && time < 300) {
				this.$emit('click', {
					content: item,
					index,
					position
				})
			}
		},
		onClickForPC(index, item, position) {
			// #ifdef H5
			if(!isPC()) return
			this.$emit('click', {
				content: item,
				index,
				position
			})
			// #endif
		},
		getButtonSize() {
			const views = uni.createSelectorQuery().in(this)
			views
				.selectAll('.uni-swipe_button-group')
				.boundingClientRect(data => {
					let show = 'none'
					if (this.autoClose) {
						show = 'none'
					} else {
						show = this.show
					}
					this.button = {
						data,
						show
					}
				})
				.exec()
		}
	}
}
