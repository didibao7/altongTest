<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
    .vue-modal-mask {
        position: fixed;
        z-index: 9998;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, .5);
        display: table;
        transition: opacity .3s ease;
    }

    .vue-modal-wrapper {
        display: table-cell;
        vertical-align: middle;
    }

    .vue-modal-container {
        /*max-width: 600px;*/
        margin: 0px auto;
        padding: 20px 30px;
        background-color: #fff;
        border-radius: 2px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
        transition: all .3s ease;
        font-family: Helvetica, Arial, sans-serif;
    }

    .vue-modal-container-no-padding {
        padding: 0;
    }

    .vue-modal-header {
        margin-top: 0;
        margin-bottom: 0;
        color: #42b983;
    }

    .vue-modal-body {
        margin: 20px 0;
        padding: 15px;
        padding-top: 0px;
    }

    .vue-modal-footer {
        padding: 15px;
        padding-top: 0px;
    }

    .vue-modal-default-button {
        float: right;
    }

    .vue-modal-enter {
        opacity: 0;
    }

    .vue-modal-leave-active {
        opacity: 0;
    }

    .vue-modal-enter .modal-container,
    .vue-modal-leave-active .modal-container {
        -webkit-transform: scale(1.1);
        transform: scale(1.1);
    }
</style>
<template id="vue-modal-template">
    <transition name="vue-modal">
        <div class="vue-modal-mask">
            <div class="vue-modal-wrapper" @click.self="$emit('close')">
                <div class="vue-modal-container" :class="{'vue-modal-container-no-padding': !padding}" :style="modalContainerStyle">
                    <div class="vue-modal-header">
                        <slot name="header">
                            default header
                        </slot>
                    </div>

                    <div class="vue-modal-body">
                        <slot name="body">
                            default body
                        </slot>
                    </div>

                    <div class="vue-modal-footer">
                        <slot name="footer">
                            default footer
                            <button class="vue-modal-default-button btn btn-default" @click="$emit('close')">
                                OK
                            </button>
                        </slot>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>
<script>
    Vue.component('modal', {
        template: '#vue-modal-template',
        props: {
            padding: {
                type: Boolean,
                default: true
            },
            width: {
                type: String,
                default: '600px'
            }
        },
        computed: {
            modalContainerStyle: function () {
                return {
                    maxWidth: this.width,
                };
            }
        },
    });
</script>