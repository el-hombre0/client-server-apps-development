const { expect } = require('chai');
const { ethers } = require('hardhat');

describe("shop", function(){
    let acc1
    let acc2
    let shopobj

    beforeEach(async function(){
        [acc1, acc2] = await ethers.getSigners()
        const shop = await ethers.getContractFactory("shop", acc1)
        shopobj = await shop.deploy()
        await shopobj.waitForDeployment()
        console.log(shopobj.address)
    })
    it("should be deployed", async function(){
        console.log('success!');
    })
})